# TDT4240 - Gålf

Gålf is surprisingly a Golf-like 2D game for Android, with support for  both multiplayer and single-player.

The goal of the game is to get from the starting point to the flag in the least amount of shots possible. In order to reach the goal, the player will have to maneuver rough terrain and movable objects. Colliding with terrain or objects will cause the ball trajectory to change. If the goal is not reached within X shots, the hole is considered failed. The game itself will consist of map packs, where a map pack can contain any number of holes/levels.

In multiplayer, there will be additional configuration options. Players can for instance choose to enable ball collision, so that the competing players can shoot each other out of the way and be nuisances to one another.

Both game modes will also support changing ball shapes. Planned shapes are squares and triangles. These alternative shapes can impact the size and speed of the ball, whilst also affecting how collisions will impact ball trajectory.

The ball object will be controlled with touch \& drag, where a player can touch and drag in the opposite direction of where they intend to go. The power of the shot is dependent on how far the player decides to drag. A tiny indicator will give the player a general idea of their shot trajectory and strength.

![Android CI](https://github.com/Jonas-C/TDT4240-Gaalf/workflows/Android%20CI/badge.svg)
