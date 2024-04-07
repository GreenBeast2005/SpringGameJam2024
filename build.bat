cls
@echo off
javac Game.java View.java Controller.java Model.java Json.java Sprite.java Grid.java Crop.java Tool.java Interactable.java Bag.java Floor.java DayCycleTrigger.java FarmLand.java Hand.java Hoe.java Interactable.java Player.java Radish.java SellingInteractable.java Wall.java WateringCan.java Wheat.java

if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)

