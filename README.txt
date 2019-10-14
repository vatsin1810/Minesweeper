=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: suchak
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays - To set up the board and also clear the empty blocks, I had to manipulate 2D arrays
  of Block Objects. To set up the board, I had to randomly assign mines to spots in the array, which
  were not already assigned a mine. I also had to open a random empty block for the game to start. 
  To clear empty blocks, I had to look at all the neighbours of a given block and conduct the same 
  recursive on them as well. 

  2. Recursion - I had develop a recursive code to clear blocks that have no neighbouring mines. I 
  uncover a given block and then call the recursive function on all of its neighbours. If the 
  neighbour is a mine, then we do nothing. If the neighbour needs to display a neighbour, then only
  that number is displayed and there is no recursive call. If the neighbour is an empty block, then
  the recursive call is done on all of its neighbours too. This helps make the game 

  3. File I/O - I have a file that keeps lowest times and I write every successful time ever 
  recorded into it with a simple comma-separated values. I also display the top 5 times if the user 
  wins themselves. 

  4. JUnit Testable Component - The game has an internal model behind it that can be tested without
  having to actually play the game. Each aspect of uncovering blocks, flagging them, and retreiving
  information about them can be simulated using methods in the GameBoard class which in turn calls
  methods from the Block class. All the tests are contained within the GameTest class.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  GameBoard - Extends JPanel and contains all the functionality of the game. It has the buttons for
  new game, instructions, label for covered blocks, label for time, and the main board itself. It
  handles all the mouse events and also contains and handles the internal model of the game. The 
  internal model is a 2D array of Block objects (see below) which is altered based on the mouse
  events. It has methods for resetting the board, starting a game, clearing blocks, ending the game 
  and handles all the option/message windows that interact with the user.
  
  Block - Extends JComponent and knows how to display itself based on its current state. GameBoard
  changes this state (i.e. Block does not know when its being clicked). It stores all the state like
  whether it is covered, it is a mine, it is flagged etc. GameBoard also sets the number of 
  neighbouring mines a block has as no Block object knows about its place in the 2D array.
  
  Game - This class just runs the game, but it packs the frame every 100 milliseconds, because the
  user has the capability to change the size of the board when they start a new game. I could have
  just setResizable(false) for this but that would require the JFrame to know the state of the game
  and then alter itself accordingly. This is a much easier fix for this issue and does not hamper
  the gameplay.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  Initially, Block extended JButton and would change its state based on whether it was clicked. 
  However, GameBoard needed to know when a specific Block object was clicked so that it could clear
  the blocks around that block. It also needed to keep track of how many blocks are uncovered so 
  that it could know when the game ended. Hence, there was a point when only the aesthetics of the 
  game were working, but it didn't know when the game ended or a new game was started. I first tried 
  making Block an inner class in GameBoard but it just made the code very messy. So instead, I 
  decided to handle all the mouse events from GameBoard itself, so that it could alter the Block 
  objects itself while being able to keep track of all the vital details. I ran into the same issue
  when allowing the users to change the size of the frame. The Game class would need to change the
  size of the frame now whenever the user starts a new game with a different size. To fix this, I
  packed the frame every 100 milliseconds in the Game class so that the frame is always the correct 
  size.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  My code has not been separated well at all and that is first thing I would work on if given the 
  chance to refactor. GameBoard handles pretty much everything and I could separate the interaction
  between the user and the game into a different class. The topPane could also be separated into
  a different class as it only consists of buttons and a timer (which would need to communicate with
  the GameBoard class). However, I feel private state has been encapsulated well and I have getters
  and setters for each field that I need to change.



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  https://commons.wikimedia.org/wiki/File:Minesweeper_0.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_1.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_2.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_3.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_4.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_5.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_6.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_7.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_8.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_flag.svg
  https://commons.wikimedia.org/wiki/File:Minesweeper_unopened_square.svg
  https://www.appannie.com/en/apps/ios/app/minesweeper-i-my-mine/
