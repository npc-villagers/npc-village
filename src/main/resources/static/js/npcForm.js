'use strict';

// This function updates the subspecies dropdown when a species is selected
function loadSubspecies(speciesName) {
    axios.get('/species/' + speciesName)
        .then(function(response) {
            let subspeciesSelect = document.getElementById('subspecies');
            subspeciesSelect.options.length = 0; // remove any existing options

            response.data.forEach(subspecies => {
                let option = document.createElement('option');
                option.text = subspecies;
                option.value = subspecies;
                subspeciesSelect.add(option);
            });
        })
        .catch(error => {
                if (error.response && error.response.status === 404) {
                    console.error('Species not found!');
                } else {
                    console.error('An error occurred!');
                }
            });
}

// This function shows or hides the customAge field based on the selected age
function toggleCustomAge() {
    let ageSelect = document.getElementById('age');
    let customAge = document.getElementById('customAgeDiv');
    if (ageSelect.value === 'CUSTOM') {
        customAge.style.display = 'block';
    } else {
        customAge.style.display = 'none';
    }
}

// This function shows or hides the customOccupation field based on the selected occupation
function toggleCustomOccupation() {
    let occupationSelect = document.getElementById('occupation');
    let customOccupation = document.getElementById('customOccupationDiv');
    if (occupationSelect.value === 'CUSTOM') {
        customOccupation.style.display = 'block';
    } else {
        customOccupation.style.display = 'none';
    }
}

// Populate subspecies on page load
window.addEventListener('load', function() {
    // The following code will let the default values in text fields be highlighted completely when a user clicks into it. Any other value than the default "Any" will not trigger the event listener
    let inputs = document.querySelectorAll('input[type=text]');

    inputs.forEach(function(input) {
        var handler = function(event) {
            if (event.target.value === 'Any') {
                setTimeout(() => event.target.select(), 0);
            }
        };

        input.addEventListener('focus', handler);
    });

    // Get the selected species value
    let speciesSelect = document.getElementById('species');
    let selectedSpecies = speciesSelect.value;

    // Call loadSubspecies with the selected species value
    loadSubspecies(selectedSpecies);
});

// Add event listener to update the subspecies option
document.getElementById('species').addEventListener('change', function() {
    loadSubspecies(this.value);
});
document.getElementById('age').addEventListener('change', toggleCustomAge);
document.getElementById('occupation').addEventListener('change', toggleCustomOccupation);
