$(document).ready(function (e) {
    // Event handler for button click
    $('#getValues').click(function (e) {
        e.preventDefault();

        // Get the selected values from the Chosen multi-select
        var selectedValues = $('.chosen-select').val();

        // Display the selected values
        if (selectedValues) {
            alert('Selected values: ' + selectedValues.join(', '));
        } else {
            alert('No options selected');
        }
    });
});