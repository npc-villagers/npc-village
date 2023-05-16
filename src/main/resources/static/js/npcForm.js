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

// Add event listener to update the subspecies option
document.getElementById('species').addEventListener('change', function() {
    loadSubspecies(this.value);
});

// Populate subspecies on page load
window.addEventListener('load', function() {
    // Get the selected species value
    let speciesSelect = document.getElementById('species');
    let selectedSpecies = speciesSelect.value;

    // Call loadSubspecies with the selected species value
    loadSubspecies(selectedSpecies);
});