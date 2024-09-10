
//add employee
$(document).ready(function(){
    $('#submitForm').on('click',function(e){
        e.preventDefault();
        var formData = new FormData ($('#addEmployee')) 
    })
    $.ajax({
        type: 'POST',
        url: 'http://localhost:9999/employee', // Đường dẫn API của server
        data: JSON.stringify(Object.fromEntries(formData)),
        contentType: 'application/json',
        processData: false,
        success: function(response) {
          alert('Data saved successfully');
        },
        error: function(error) {
          console.log('Error:', error);
        }
    })
})