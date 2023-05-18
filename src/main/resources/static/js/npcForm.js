'use strict';

// This function updates the subspecies dropdown when a species is selected, and loads the saved subspecies if it's set
function loadSubspecies(speciesName) {
    axios.get('/species/' + speciesName)
        .then(function(response) {
            let subspeciesSelect = document.getElementById('subspecies');
            subspeciesSelect.options.length = 0; // remove any existing options
            let savedSubspecies = subspeciesSelect.getAttribute('data-saved-value');
            let filterAny = subspeciesSelect.getAttribute('data-filter-any') === 'true';

            response.data.forEach(subspecies => {
                // Add conditional check for "Any" subspecies
                if (!filterAny || (filterAny && subspecies !== 'Any')) {
                    let option = document.createElement('option');
                    option.text = subspecies;
                    option.value = subspecies;
                    subspeciesSelect.add(option);
                    if (option.value === savedSubspecies) {
                        option.selected = true;
                    }
                }
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
    let ageSelect = document.getElementById('ageCategory');
    let customAge = document.getElementById('customAgeDiv');
    if (ageSelect.value === 'CUSTOM') {
        customAge.style.display = 'block';
    } else {
        customAge.style.display = 'none';
    }
}

// This function shows or hides the customOccupation field based on the selected occupation
function toggleCustomOccupation() {
    let occupationSelect = document.getElementById('occupationCategory');
    let customOccupation = document.getElementById('customOccupationDiv');
    if (occupationSelect.value === 'CUSTOM') {
        customOccupation.style.display = 'block';
    } else {
        customOccupation.style.display = 'none';
    }
}

// Populate subspecies on page load and add a nice-to-have functionality in our text fields that highlights the whole field if it's a default value, instead of placing the cursor at the end
window.addEventListener('load', function() {
    // Get the selected species value
    let speciesSelect = document.getElementById('species');
    let selectedSpecies = speciesSelect.value;

    // Call loadSubspecies with the selected species value
    loadSubspecies(selectedSpecies);

    // The following code will let the default values in text fields be highlighted completely when a user clicks into it. Any other value than the default "Any" will not trigger the event listener
    let inputs = document.querySelectorAll('input[type=text]');

    inputs.forEach((input) => {
        let handler = (event) => {
            if (event.target.value === 'Any') {
                setTimeout(() => event.target.select(), 0);
            }
        };

        input.addEventListener('focus', handler);
    });

    // In the case of our edit form, we have custom as the first option, so we want to call these methods on page load to check their initial value
    toggleCustomAge()
    toggleCustomOccupation()
});

// Add event listeners to update the subspecies option, and conditionally display custom age and occupation options
document.getElementById('species').addEventListener('change', function() {
    loadSubspecies(this.value);
});
document.getElementById('age').addEventListener('change', toggleCustomAge);
document.getElementById('occupation').addEventListener('change', toggleCustomOccupation);