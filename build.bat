cls
@echo off
javac Game.java View.java Controller.java Model.java Json.java Sprite.java Grid.java Crop.java Tool.java Interactable.java Bag.java

if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)

