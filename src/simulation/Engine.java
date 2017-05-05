/**
 * 
 */
package simulation;


import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import evaluation.TokenEvaluator;
import rules.generator.ConcreteMoveGenerator;
import rules.phasegroup.PhaseGroup;
import search.IterativeDepthFirstSearcher;
import search.Searcher;
import state.board.Board;
import state.nation.Nation;
import statetransition.Move;
import staticstate.Structure;
import staticstate.StructureFactory;
import staticstate.rules.RulesStructure;
import staticstate.state.StateStructure;
import util.Constants;

/**
 * Encapsulates all the parts that make a simulation operate.
 * 
 * @author ngales
 */
public class Engine {

  private Board gameState;
  private PhaseGroup rulesState;
  private Set<Nation> agents;
  private Searcher searcher;

  // private MoveGenerator generator;

  /*
   * Board is state, rules (and phases) are separate and act as drivers to state changes, by acting
   * as move generators.
   * 
   * Then, it would make sense to say that the player to play (and play order) be distinct from the
   * board state. However, both the board and rules are necessary to describe any single point in a
   * game.
   * 
   * Board represents the areas and tokens on a board. MoveGenerator represents a way to get a set
   * of all possible next states from a board, and needs rules as a requirement (ie 4 or 5 moves for
   * ships, 1 or 2 moves for land tokens). MoveGenerator thus is a rule, and should be able to be
   * modular for land tokens and for ship tokens and for city tokens. So, pass in a
   * LandMoveGenerator, ShipMoveGenerator, CityMoveGenerator. To what? To..
   * 
   * All needed parts of game are: Board Area Info Tokens (land, ship, city) Owner Area edges (land,
   * water)
   * 
   * Phase Order (List of Phase) Phase Nation order (List of Nation) Place in nation order (index to
   * list) MoveGenerator for Phase Place in phase order (index to list)
   * 
   * Trade Card stacks List of lists of cards, ordered, incl. calamities
   * 
   * Advances Set of advances for each Nation
   */

  public static final String DEFAULT_STRUCTURE = "Grid";

  private Engine(Board gameState, PhaseGroup rulesState, Searcher searcher) {
    Preconditions.checkNotNull(gameState);
    Preconditions.checkNotNull(gameState.getAgents());
    Preconditions.checkNotNull(rulesState);
    Preconditions.checkNotNull(searcher);
    this.gameState = gameState;
    this.rulesState = rulesState;
    this.agents = ImmutableSet.copyOf(gameState.getAgents());
    this.searcher = searcher;
  }

  private Engine() {
    Structure structure = StructureFactory.getInstance(DEFAULT_STRUCTURE);
    StateStructure stateStructure = structure.getStateStructure();
    RulesStructure rulesStructure = structure.getRulesStructure();

    gameState = stateStructure.getBoardType().getInstance(stateStructure);
    rulesState = rulesStructure.getPhaseGroupType().getInstance(rulesStructure);

    searcher =
        IterativeDepthFirstSearcher.getInstance(TokenEvaluator.getInstance(),
            rulesState.movesInCycle());
    // searcher = DepthFirstSearcher.getInstance();
    // searcher = RandomSearcher.getInstance();
    // searcher = SingleDepthSearcher.getInstance();

    /*
     * // Game initial setup (Move somewhere) Area initialArea = (new
     * ArrayList<Area>(gameState.getAreas())).get(0); Structure structure =
     * StructureFactory.getInstance(structureString); List<NationInfo> nationInfoList = new
     * ArrayList<NationInfo>(structure.getNationInfos()); List<Nation> nationList = new
     * ArrayList<Nation>(nationInfoList.size()); for (NationInfo nationInfo : nationInfoList) {
     * Nation nation = StandardNation.getInstance(nationInfo); nationList.add(nation); }
     * gameState.moveToken(nationList.get(0), gameState.getStock(), initialArea);
     * gameState.moveToken(nationList.get(1), gameState.getStock(), initialArea);
     */

    // TODO: nations are shared between rules and state, but state doesn't care about order, while
    // rules does. both need to be able to keep track of what they care about. should they both keep
    // their own list/set, or should it be kept in a level of abstraction above here?

    // maybe it should be, since structure specifies both rules and state, and thus would need to be
    // pulled here anyway.

    // rules needs:
    // each phase with which to use (incl. sequence of nations in phase through which to cycle)
    // sequence of the phases through which to cycle

    // state needs:
    // collection of nations which have pieces on the board
    // initial number of pieces for each nation
    // collection of areas on the board
    // adjacencies between areas

    // phaseState = SimplePhaseGroup.getInstance( nationList );

    System.out.print("Engine created, initial game state..\n");
    System.out.println(gameState);
    System.out.println(rulesState);
    System.out.println(searcher);
    System.out.println(Constants.TERMINAL_SEPERATOR);

    // TODO: figure some way to include just a single phase execution of placement in a phasegroup
    for (Nation nation : rulesStructure.getNations()) {
      Set<Move> moves = ConcreteMoveGenerator.PLACEMENT.generateMoves(gameState, nation);
      moves.iterator().next().applyMove();
    }

    System.out.print("After placement..\n");
    System.out.println(gameState);
    System.out.println(rulesState);
    System.out.println(Constants.TERMINAL_SEPERATOR);
  }

  /**
   * Returns an Engine in its initial state.
   * 
   * @return
   */
  public static Engine getInstance() {
    Structure structure = StructureFactory.getInstance(DEFAULT_STRUCTURE);
    StateStructure stateStructure = structure.getStateStructure();
    RulesStructure rulesStructure = structure.getRulesStructure();

    Board gameState = stateStructure.getBoardType().getInstance(stateStructure);
    PhaseGroup rulesState = rulesStructure.getPhaseGroupType().getInstance(rulesStructure);

    Searcher searcher =
        IterativeDepthFirstSearcher.getInstance(TokenEvaluator.getInstance(),
            rulesState.movesInCycle());

    assert(gameState.getAgents().equals(rulesStructure.getNations()));
    
    return new Engine(gameState, rulesState, searcher);
  }

  public static Engine getInstance(Board gameState, PhaseGroup rulesState, Searcher searcher) {
    // defer precondition checks to constructor
    return new Engine(gameState, rulesState, searcher);
  }

  public boolean applyMove(Move move) {
    // game state before move should be same as after previous move (searcher should be idempotent)

    // boolean moveSuccess = LogMove.getInstance(move).applyMove();
    boolean moveSuccess = move.applyMove();

    System.out.print("game state after move..\n");
    System.out.println(this);

    return moveSuccess;
  }

  /**
   * Generates the set of possible moves from this engine's current board and rules state.
   * Guaranteed to be non-empty.
   * 
   * @return Set of one or more possible moves from current state.
   */
  public Set<Move> getMoves() {
    Set<Move> moves = rulesState.generateMoves(gameState);
    if (moves.size() == 0) {
      throw new IllegalStateException("moves size cannot be 0");
    }
    return moves;
  }

  // TODO: maybe this should be in simulation..
  public Move findBestMove() {
    // TODO: Move this to a better place, maybe searcher, if a way to fit
    // it in without disrupting logic can be found

    // if next move is a singleton, no need to search for best
    Set<Move> moves = getMoves();

    Move move = null;
    if (moves.size() == 1) {
      move = moves.iterator().next();
    } else {
      move = searcher.findBestMove(gameState, rulesState);
    }
    System.out.println("found best move: " + move);
    return move;
  }

  public boolean stopped() {
    return gameState.hasEnded();
  }

  public Board getState() {
    return gameState;
  }

  public Set<Nation> getAgents() {
    return agents;
  }

  public PhaseGroup getRules() {
    return rulesState;
  }

  public Nation toPlay() {
    return rulesState.toPlay();
  }

  public Nation stateAgent() {
    return rulesState.stateAgent();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(gameState);
    sb.append("\n");
    sb.append(rulesState);
    sb.append("\n");
    sb.append(TokenEvaluator.getInstance().eval(gameState, rulesState));
    sb.append("\n");
    sb.append(Constants.TERMINAL_SEPERATOR);
    return sb.toString();
  }
}
