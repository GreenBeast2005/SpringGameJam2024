cls
@echo off
javac Game.java View.java Controller.java Model.java Json.java Wall.java Pacman.java Ghost.java Pellet.java Fruit.java Sprite.java

if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)

