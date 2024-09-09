

// When the user submits the form to check room availability
$(document).ready(function () {
    $('.result-placeholder').click(function (e) {
        e.preventDefault(); // prevent slide up the UI

        // Capture input values from the form
        let checkIn = $('#checkIn').val();
        let checkOut = $('#checkOut').val();
        let adults = $('#adults').val();
        let children = $('#children').val();

        // Validation: Ensure all fields are filled in
        if (!checkIn || !checkOut || !adults || children === '') {
            alert("Please fill in all fields");
            return;
        }

        // Format dates to YYYY-MM-DD and remove any extra time info
        let formattedCheckIn = new Date(checkIn).toISOString().split('T')[0];
        let formattedCheckOut = new Date(checkOut).toISOString().split('T')[0];

        // Prepare data to be sent in the request
        let requestData = {
            "checkIn": formattedCheckIn,
            "checkOut": formattedCheckOut,
            "adultNumber": adults,
            "childrenNumber": children
        };

        // Log the request data for debugging
        console.log(requestData);

        // Make an AJAX request to the API
        $.ajax({
            url: 'http://localhost:9999/room',
            type: 'GET',
            data: requestData, // Send form-data
            success: function (response) {
                console.log("Response from API:", response.data);

                // Store the room data in localStorage
                localStorage.setItem('roomData', JSON.stringify(response.data));

                // Redirect the user to the room-2.html page
                window.location.href = 'room-2.html';
            },
            error: function (error) {
                console.error(error);
                alert("Error: Could not retrieve room data.");
            }
        });
    });
});

// When the room-2.html page loads
$(document).ready(function () {
    // Retrieve the room data from localStorage
    let roomData = JSON.parse(localStorage.getItem('roomData'));

    // Check if roomData exists
    if (!roomData) {
        alert('No rooms available.');
        return;
    }

    // Log the room data for debugging
    console.log(roomData);

    // Get the container where the rooms will be displayed
    let roomsContainer = $('#roomTypeList');

    // Clear any existing content
    roomsContainer.empty();

    // Iterate over the room data and create HTML for each room
    roomData.forEach(function (room) {
        //let availableRoomCount = room.bedType.roomAvailableDTOList != null ? room.bedType.roomAvailableDTOList.length : 0;
        let roomCard = `
            <div class="col-xl-4 col-md-6" data-aos="fade-up" data-aos-duration="1500">
                <div class="rooms-card">
                    <img src="${room.image[0]}" alt="room">
                    <div class="details">
                        <h3>Room ${room.roomTypeName}</h3>
                        <span>$${room.price} / Night</span>
                        <ul>
                            <li><i class="ri-group-line"></i> ${room.capacity} Persons</li>
                            <li><i class="ri-hotel-bed-line"></i> ${room.bedType.name}</li>
                            <li>
    <i class="ri-hotel-line"></i> <!-- Icon hình phòng từ Remix Icon -->
    <strong style="color: red;">${room.numberAvailable} rooms available</strong>
</li>

                            
                            <li><i class="mdi mdi-pool"></i> Swimming Pool</li>
                            <li><i class="ri-wifi-line"></i> Free Wifi</li>
                        </ul>
                        <a href="#" class="lh-buttons-2">View More <i class="ri-arrow-right-line"></i></a>
                    </div>
                </div>
            </div>
        `;
        // Append the room card to the container
        roomsContainer.append(roomCard);
    });
});
//<li><i class="ri-restaurant-2-line"></i> There are currently ${room.numberAvailable} rooms available </li>