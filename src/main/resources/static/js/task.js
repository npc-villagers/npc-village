'use strict';

// The purpose of this script is to poll the task that is associated with the OpenAI request. It is attached to the loading template, and every few seconds, a random loading phrase is provided to the user for user experience.

const loadingPhrases = [
  "Scrying through the planes of existence...",
  "Invoking the power of the Archmage...",
  "Decoding the Draconic script...",
  "Negotiating with the Gnome Tinkerer...",
  "Weaving through the Ethereal Plane...",
  "Asking directions from a Treant...",
  "Bargaining with the Djinn for answers...",
  "Scouting the Underdark...",
  "Rousing Tiamat for a quick chat...",
  "Cross-referencing with the Great Library of Candlekeep...",
  "Recharging the Staff of Power...",
  "Crossing the Mists into Barovia...",
  "Summoning an information elemental...",
  "Disarming the knowledge traps in the Tomb of Horrors...",
  "Decoding the Aboleth's telepathic messages...",
  "Bargaining with Mordenkainen for arcane secrets...",
  "Traversing the jungles of Chult for lost data...",
  "Deciphering the Book of Vile Darkness...",
  "Navigating the Nine Hells for information...",
  "Conjuring a familiar to scout for information...",
  "Borrowing the Eye of Vecna for better insight...",
  "Distracting the Beholder with a riddle...",
  "Scribing scrolls of data extraction...",
  "Casting Zone of Truth on the database...",
  "Consulting the Great Druid for natural wisdom...",
  "Interrogating the captive Drow spy...",
  "Summoning an arcane gate to the Plane of Knowledge...",
  "Reading the Deck of Many Things...",
  "Casting Divination for the path to success..."
];

const usedPhrases = [];

window.onload = function() {
  const taskId = document.querySelector('script[src="/js/task.js"]').getAttribute('data-taskId');
  displayLoadingPhrase(); // Display initial loading phrase
  checkStatus(taskId);
}

function displayLoadingPhrase() {
  const loadingPhrase = getRandomPhrase();
  const loadingPhraseElement = document.getElementById('loadingPhrase');
  loadingPhraseElement.textContent = loadingPhrase;

  setTimeout(displayLoadingPhrase, 5000); // Display a new phrase every 3 seconds
}

function getRandomPhrase() {
  if (usedPhrases.length === loadingPhrases.length) {
    usedPhrases.length = 0; // Reset used phrases if all have been used
  }

  let randomIndex;
  do {
    randomIndex = Math.floor(Math.random() * loadingPhrases.length);
  } while (usedPhrases.includes(randomIndex));

  usedPhrases.push(randomIndex);
  return loadingPhrases[randomIndex];
}

function checkStatus(taskId) {
  axios.get('/checkStatus/' + taskId)
      .then(function (response) {
          const data = response.data;
          if (data.completed) {
              console.log(data.npcId);
              window.location.href = "/create/" + data.npcId;
          } else if (data.error) {
              window.location.href = "/errorPage?errorMessage=" + encodeURIComponent(data.error);
          } else {
              // Task is not completed and there is no error, so check again after 5 seconds
              setTimeout(function() { checkStatus(taskId); }, 5000);
          }
      })
      .catch(function (error) {
          console.log(error);
      });
}
