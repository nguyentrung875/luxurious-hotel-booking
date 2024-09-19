$(document).ready(function(){
    $(document).ready(function() {
        // Function to fetch employees
        function fetchEmployees() {
            $.ajax({
                url: 'http://localhost:9999/employee',  // The API endpoint for employees
                method: 'GET',
                success: function(response) {
                    if (response.statusCode === 200) {
                        let employees = response.data;
                        let employeeContainer = $('#employeeContainer');  // The div where employee cards will be inserted
                        employeeContainer.empty();  // Clear existing employee cards
    
                        // Loop through each employee and append to the container
                        $.each(employees, function(index, employee) {
                            let employeeCard = `
                                <div class="col-xxl-4 col-xl-4 col-md-6">
                                    <div class="role-card">
                                        <div class="lh-card lh-role">
                                            <div class="lh-card-header">
                                                <h4 class="lh-card-title">${employee.role}</h4>
                                            </div>
                                            <div class="lh-card-content label-card">
                                                <div class="lh-role-item">
                                                    <div class="team-detail">
                                                        <img src="${employee.imageUrl}" alt="user">
                                                        <div class="info">
                                                            <h6>${employee.name}</h6>
                                                            <p>${employee.email}</p>
                                                        </div>
                                                    </div>
                                                    <div class="lh-tool">
                                                        <div class="dropdown">
                                                            <button class="btn btn-secondary dropdown-toggle" type="button"
                                                                id="dropdownMenu1" data-bs-toggle="dropdown" aria-expanded="false">
                                                                <i class="mdi mdi-dots-vertical"></i>
                                                            </button>
                                                            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                                                <li><button class="dropdown-item" type="button">Action</button></li>
                                                                <li><button class="dropdown-item" type="button">Rename</button></li>
                                                                <li><button class="dropdown-item" type="button">Setting</button></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;
                            
                            employeeContainer.append(employeeCard);  // Add the new card to the container
                        });
                    } else {
                        console.error('Failed to fetch employees');
                    }
                },
                error: function(error) {
                    console.error('Error:', error);
                }
            });
        }
    
        // Call the function to fetch employees when the page loads
        fetchEmployees();
    });
    
})