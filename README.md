# npc-village

**Version**: 0.0.0

## Overview

Introducing NPC Village - your ultimate tool for Dungeons and Dragons campaign management! With our pioneering digital platform, you can effortlessly generate Non-Player Characters (NPCs) that add depth and intrigue to your game world.

No more spending countless hours crafting characters and their intricate backstories. NPC Village, powered by the advanced AI of ChatGPT, makes it a cinch to create compelling characters that enhance your campaign and captivate your players. Whether you need a villainous mastermind, a cryptic oracle, or a humble tavern-keeper, our platform has you covered.

## Getting Started

As of v1.0, this is an undeployed application. To use the app, you will need the following:

1. A local installation of PostgreSQL. Instructions for installation can be found [here](https://www.postgresql.org/download/).
2. Your own OpenAI API key. You can sign up and get your API key [here](https://platform.openai.com/).
3. JDK 17 installed on your local machine. If you do not have it installed, you can download it from [here](https://jdk.java.net/17/).
4. Gradle installed on your local machine. If you do not have it installed, you can download it from [here](https://gradle.org/install/).

Once you have the above requirements, follow these steps:

1. Clone this repository to your local machine.
2. Open the application.properties file and replace "your-database-url" with your local PostgreSQL database URL, and "your-openai-api-key" with your OpenAI API key.
3. Run `gradle bootRun` in your terminal at the root directory of the project.
4. Open your web browser and navigate to `http://localhost:8080`.

## Architecture

This is a web application using Spring MVC and Thymeleaf that utilizes the OpenAI API to allow a user to generate NPCs with a wide range of required characteristics. PostgreSQL allows a user on the frontend to perform CRUD operations on all of their favorite villagers.

### Technologies

- Java 17
- Gradle
- Spring MVC
- Thymeleaf
- Spring Security
- PostgreSQL
- [OpenAI API](https://platform.openai.com/)

## Change Log

2023-05-18T20:00-08:00 - v1.0 (Placeholder)

## Credit and Collaborations

### Team Members

- Alex Carr
- Dasha Burgos
- Davey Oswald
- Matt Austin
- Ryan Apodaca
