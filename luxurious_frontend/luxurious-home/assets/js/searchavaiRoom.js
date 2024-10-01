

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
    // if (!roomData) {
    //     alert('No rooms available.');
    //     return;
    // }

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
                    <a href="#" class="view-more-btn lh-buttons-2" data-room-id="${room.id}">View More <i class="ri-arrow-right-line"></i></a>
                        <h3>${room.roomTypeName}</h3>
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
                        
                    </div>
                </div>
            </div>
        `;
        // Append the room card to the container
        roomsContainer.append(roomCard);
    });



    $('.view-more-btn').on('click', function (e) {
        e.preventDefault(); // Prevent default anchor behavior
    
        // Get the roomTypeId from the button's data attribute
        let roomTypeId = $(this).attr('data-room-id');

    
console.log(roomTypeId)

        // Make an AJAX call to get room type details by ID
        $.ajax({
            url: `http://localhost:9999/roomType/${roomTypeId}`,  // Update the URL as needed
            type: 'GET',
            success: function (response) {
                // Assuming the API returns the room details
                console.log('Room details:', response);
    
                // Save the room details in localStorage
                localStorage.setItem('roomDetails', JSON.stringify(response));
    
                // Redirect to the room-details.html page
                window.location.href = 'room-details.html';
            },
            error: function (xhr, status, error) {
                
                alert('Failed to retrieve room details. Please try again.');
            }
        });
    });

});

$(document).ready(function () {
    // Retrieve the room details from localStorage
    let roomDetails = JSON.parse(localStorage.getItem('roomDetails'));

    // Kiểm tra nếu roomDetails không có
    if (!roomDetails || !roomDetails.data) {
        alert('No room details found.');
        return;
    }

    // Log để kiểm tra dữ liệu
    console.log(roomDetails);

    // 2. Cập nhật nội dung của các phần tử HTML với dữ liệu từ roomDetails.data
    $('#nameRoomType').text(roomDetails.data.name); // Tên phòng
    $('.lh-room-details-contain p').text(roomDetails.data.overview || "No overview available."); // Mô tả phòng
    $('#roomTypePrice').text(roomDetails.data.price + " $" || "No Price available.");

    // Xử lý phần tiện ích
    if (roomDetails.data.amenity) {
        let amenitiesArray = roomDetails.data.amenity.split(','); // Tách tiện ích bằng dấu phẩy

        // 3. Tạo chuỗi HTML để hiển thị tiện ích dưới dạng danh sách kèm tiêu đề
        let amenitiesHTML = '';
        amenitiesArray.forEach(function (amenity) {
            amenitiesHTML += `<li><code>*</code> ${amenity.trim()}</li>`;
        });
        //amenitiesHTML += '';

        // 4. Chèn danh sách tiện ích vào phần tử HTML
        $('.lh-room-details-amenities .row').html(amenitiesHTML);
    } else {
        // Nếu không có tiện ích, hiển thị thông báo
        $('.lh-room-details-amenities .row').html('<h4 class="lh-room-inner-heading">Amenities</h4><p>No amenities available.</p>');
    }

    // Hiển thị tiêu đề và danh sách phòng trống
    //$('#availableRoomTitle').text("Available Rooms");

    // 3. Kiểm tra và hiển thị danh sách các phòng (roomName)
    if (roomDetails.data.roomName && roomDetails.data.roomName.length > 0) {
        let roomNameHTML = '<h4 class="lh-room-inner-heading">Available Rooms</h4>';
        roomDetails.data.roomName.forEach(function (room, index) {
            // Thêm class "shake" và "shake-X" để áp dụng độ trễ khác nhau
            roomNameHTML += `<li class="shake shake-${index}">${room}</li>`;
        });
        $('#availableRoomList').html(roomNameHTML);
    } else {
        $('#availableRoomList').html('<li>No rooms available</li>');
    }

    $('#bedType').text(`Bed Type: ${roomDetails.data.bedName}`); 


   // Load hình ảnh từ JSON vào gallery
   if (roomDetails.data.image && roomDetails.data.image.length > 0) {
    // Hiển thị hình ảnh đầu tiên như là main image
    $('#largeRoomImage').attr('src', roomDetails.data.image[0]);

    // Tạo chuỗi HTML cho gallery thumbnail
    let thumbnailHTML = '';
    roomDetails.data.image.forEach(function (imageUrl, index) {
        thumbnailHTML += `
            <img src="${imageUrl}" alt="Room ${index + 1}" onclick="changeImage('${imageUrl}')">
        `;
    });

    // Chèn thumbnails vào gallery
    $('#thumbnailGallery').html(thumbnailHTML);
} else {
    // Nếu không có hình ảnh, hiển thị placeholder hoặc thông báo
    $('#largeRoomImage').attr('src', 'assets/img/no-image.png');
    $('#thumbnailGallery').html('<p>No images available</p>');
}


});

function changeImage(newImageUrl) {
    $('#largeRoomImage').attr('src', newImageUrl);
}


$(document).ready(function(){
    // Slider chính
    $('.slider-for').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        fade: true,
        asNavFor: '.slider-nav'
    });

    // Slider ảnh nhỏ
    $('.slider-nav').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        asNavFor: '.slider-for',
        dots: false,
        centerMode: true,
        focusOnSelect: true
    });
});





