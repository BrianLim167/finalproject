<!DOCTYPE html>
<html>

<head>
	<title>README</title>
</head>

<body>
	<center><font size="6"><b>Go Lim Woo!</b></font></center>
	<br>
	<font size="4"><b>What is our project?</b></font>
	<p>Our CS project is a a digital version of the ancient board game Go. In it, two players, represented by black and white, attempt to capture each other's pieces by surrounding groups of the opposite-color stones from all directions, excluding diagonals. The game ends when both players pass consecutively, or when one player forfeits; in the case of the former, the number of captured stones and surrounded empty spaces are counted up, and whoever has the higher number wins, while in the case of the latter, the forfeiting player automatically loses. Black always starts the game, and can have extra moves in the beginning, called a handicap. To compensate for this advantage, white gets extra points, called komi.</p>
	<font size="4"><b>List of Working Features/Things to Test</b></font>
	<ul>
		<li>Customizable parameters, including board size, komi, and number of handicap moves.</li>
		<li>Black and white pieces will automatically be removed from the board and added to the capturer's points.</li>
		<li>Pass and resign buttons will end the game and either immediately award the opponent the victory or otherwise just end the game (scores can be manually tallied).</li>
		<li>Try capturing a group of stones by surrounding it on all sides with either the opposite color or the sides of the board.</li>
		<li>Try doing a suicidal move; a suicidal move is defined as a move where if you put down a Go piece in a particular spot, it will instantly be captured due to being surrounded on all sides. It's against the official Go rules to make a suicidal move, and as such the program prevents you from doing one. However, a move that gets the stones of both players surrounded at the same time is not suicidal; in that case, the stones of the player who played the move survive and the other stones die.</li>
		<li>Additionally, try doing a <a href="http://senseis.xmp.net/?Ko">ko move</a>. A ko move is a move that would set the board to become exactly like it was two turns ago (if black does a ko move, it would set the board to become identical to how it was when it was last black's turn, for example). Like suicidal moves, ko moves are banned in a regular game of Go, and as such the program prevents you from doing one.</li>
	</ul>
	<font size="4"><b>List of Unresolved Bugs/Issues</b></font>
	<ul>
		<li>Some edge cases (such as <a href="http://senseis.xmp.net/?EternalLife">Eternal Life</a> or <a href="http://senseis.xmp.net/?TripleKo">Triple Ko</a>) exist in the actual Go game in which it's possible to loop a cycle of capturing stones forever and thus create a stalemate. There are very niche rules that forbid this from happening, but they have not been implemented into our final version of the project because these situations are extremely rare.</li>
	</ul>
	<font size="4"><b>How to Compile and Run Our Program</b></font>
	<ul>
		<li>Only Go.java needs to be compiled and run.</li>
	</ul>
	<font size="4"><b>How to Use Our Program</b></font>
	<ol>
		<li>When you first run Go.java, a setup window will pop up. In the window, you can customize the size of the board, the number of komi/bonus points white gets, and the number of handicap moves/extra first-turn moves black gets.</li>
		<li>After you click the PLAY button, the actual game will open up. Click on a spot on the board (the intersection of lines) to place down a stone.</li>
		<li>If you wish to end a game, you can either forfeit to give your opponent the victory, or you can pass twice in a row (once for black and once for white) to end the game.</li>
		<li>If the game ends due to two passes in a row, whoever has the higher score (including komi for white) wins the game.</li>
	</ol>
</body>

</html>
