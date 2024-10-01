$(document).ready( function(){
    $.ajax({
        url: "http://localhost:9999/roomType",
        method: "GET",
        contentType: "application/json",
        
    }).done(function( response ) {
          if(response.data){
            var html=""
            
            for(i=0;i<response.data.length;i++){
                var item = response.data[i];
                    var ruoomNum=""

                if(item.roomName && item.roomName.length >0){
                    for(j=0; j< item.roomName.length;j++){
                        ruoomNum += item.roomName[j];
                        if(j<item.roomName.length-1){
                            ruoomNum += ", "
                        }
                    }
                }else{
                    ruoomNum="No Rooms Available"
                }

                html+= `													<tr>
														<td class="token">${item.name}</td>
														<td><img class="cat-thumb" src="${item.image[0]}"
																alt="clients Image"><span class="name">${item.overview}</span>
														</td>
														<td>$${item.price}</td>
														<td>${item.area}m2</td>
														<td>${item.capacity}</td>
														<td>${item.bedName}</td>
                                                        <td>${ruoomNum}</td>
														
														
														
														<td>
															<div class="d-flex justify-content-center">
																<!-- <button type="button" class="btn btn-outline-success"><i
																		class="ri-information-line"></i></button> -->
																<button type="button"
																	class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
																	data-bs-toggle="dropdown" aria-haspopup="true"
																	aria-expanded="false" data-display="static">
																	<span class="sr-only"><i
																			class="ri-settings-3-line"></i></span>
																</button>
																<div class="dropdown-menu">
																	<span class="dropdown-item edit-btn" href="#" data-id="${item.id}">Edit</span>
																	<span class="dropdown-item delete-btn" href="#" data-id="${item.id}">Delete</span>
																</div>
															</div>
														</td>
													</tr>`
            }
            $('#list-roomtype').append(html)
          }
    });
})

//<td>${item.amenity}</td>




$(document).ready(function() {
    
    $('#list-roomtype').on('click', '.dropdown-item.delete-btn', function(event) {
        //event.preventDefault(); 

        var row = $(this).closest('tr'); // Lấy hàng chứa nút được nhấn
        var roomId = $(this).data('id'); // Lấy ID từ thuộc tính data-id

       
        if(confirm('Are you sure you want to delete this RoomType data?')) {
            $.ajax({
                url: "http://localhost:9999/roomType/" + roomId, 
                method: "DELETE",
                contentType: "application/json",
                success: function(response) {
                    if(response.statusCode == 200) {
                        
                        row.remove(); 
                        alert('Xóa thành công');
                    } else {
                        alert('Xóa thất bại: ' + response.message);
                    }
                },
                error: function() {
                    alert('Đã có lỗi xảy ra khi xóa');
                }
            });
        }
    });
});




// adđ front end

$(document).ready(function() {
    $('#addRoomTypeForm').on('submit', function(event) {
        event.preventDefault(); // Ngăn chặn hành động submit mặc định

        var formData = new FormData(this); // Sử dụng FormData để thu thập dữ liệu từ form



        // Lưu ý: Đảm bảo rằng input type="file" có name="images[]" nếu bạn muốn tải nhiều tệp

        $.ajax({
            url: "http://localhost:9999/roomType", // Thay thế bằng endpoint của bạn
            method: "POST",
            data: formData,
            processData: false, // Ngăn không cho jQuery xử lý dữ liệu
            contentType: false, // Không đặt kiểu contentType vì chúng ta đã dùng FormData
            success: function(response) {
                if (response.statusCode === 200) {
                    alert('Room type added successfully!');
                } else {
                    alert('Failed to add room type: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Status: " + status);
                console.error("Error: " + error);
                console.error("Response: " + xhr.responseText);
                alert('There was an error submitting the form.');
            }
        });
    });
});

// hiển thị hình ảnh tạm

$(document).ready(function() {
    $('#imageUpload').on('change', function() {
        $('#imagePreview').empty(); // Xóa hình ảnh cũ
        var files = this.files;

        if (files) {
            $.each(files, function(index, file) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    var img = $('<img>').attr('src', e.target.result).attr('class', 'img-fluid').css('margin-right', '4px').css('margin-bottom', '4px');
                    $('#imagePreview').append(img);
                }
                reader.readAsDataURL(file);
            });
        }
    });
});

// edit front end


$(document).ready(function() {
    
    $(document).on('click', '.edit-btn', function(event) {
        event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

        var roomId = $(this).data('id'); // Lấy ID của RoomType

        alert(roomId); // Kiểm tra xem roomId có đúng không

        
        //sessionStorage.setItem('editRoomId', roomId);

        //localStorage.setItem('roomData', JSON.stringify(response.data));

        localStorage.setItem('editRoomId', roomId);

        
        window.location.href = "RoomTypeUpdate.html";
    });
});


$(document).ready(function() {
     
    var imagesToDelete = [];
    var existingImages = [];  

     
    //var roomId = sessionStorage.getItem('editRoomId');
    var roomId = localStorage.getItem('editRoomId');

    if (roomId) {
        $.ajax({
            url: "http://localhost:9999/roomType/" + roomId,
            method: "GET",
            contentType: "application/json",
            success: function(response) {
                if (response.statusCode === 200) {
                    var room = response.data;

                     
                    $('input[name="id"]').val(room.id);
                    $('input[name="name"]').val(room.name);
                    $('input[name="overview"]').val(room.overview);
                    $('input[name="price"]').val(room.price);
                    $('input[name="area"]').val(room.area);
                    $('input[name="capacity"]').val(room.capacity);
                    $('select[name="iDBedType"]').val(room.bedNum);

                     
                    var roomNameString = room.roomName.join(', ');
                    $('input[name="roomName"]').val(roomNameString);

                    
                    if (room.image && room.image.length > 0) {
                        $('#imagePreview').empty(); 
                        room.image.forEach(function(imageUrl, index) {
                            var imgContainer = $('<div>').addClass('image-container').css({
                                'display': 'inline-block',
                                'position': 'relative',
                                'margin-right': '10px',
                                'margin-bottom': '10px'
                            });

                            var img = $('<img>').attr('src', imageUrl)
                                                .attr('class', 'img-fluid')
                                                .css('max-width', '100px'); 
                            
                            var removeButton = $('<button>').text('X')
                                                .attr('data-url', imageUrl)  
                                                .addClass('remove-image-btn')
                                                .css({
                                                    'position': 'absolute',
                                                    'top': '0',
                                                    'right': '0',
                                                    'background-color': 'red',
                                                    'color': 'white',
                                                    'border': 'none',
                                                    'cursor': 'pointer'
                                                });

                            imgContainer.append(img).append(removeButton);
                            $('#imagePreview').append(imgContainer);
                            
                            
                            existingImages.push(imageUrl);
                        });
                    }

                     
                    if (room.amenity) {
                        const amenityMap = {
                            "Free Wi-Fi": 1,
                            "Swimming Pool": 2,
                            "Fitness Center": 3,
                            "24-Hour Reception": 4,
                            "Room Service": 5,
                            "Laundry Service": 6,
                            "Parking": 7,
                            "Air Conditioning": 8,
                            "Breakfast Included": 9,
                            "Airport Shuttle": 10,
                            "Spa and Wellness Center": 11,
                            "Pet-Friendly": 12,
                            "Non-Smoking Rooms": 13,
                            "Conference Facilities": 14,
                            "Restaurant": 15,
                            "Bar/Lounge": 16,
                            "Business Center": 17,
                            "Cable/Satellite TV": 18,
                            "Mini-Bar": 19,
                            "Balcony/Terrace": 20
                        };

                        var amenityArray = room.amenity.split(',').map(function(item) {
                            return item.trim();
                        });

                        amenityArray.forEach(function(amenity) {
                            var amenityId = amenityMap[amenity];
                            if (amenityId) {
                                $('input[name="idAmenity"][value="' + amenityId + '"]').prop('checked', true);
                            }
                        });
                    }

                } else {
                    alert('Failed to load room type details: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Status: " + status);
                console.error("Error: " + error);
                console.error("Response: " + xhr.responseText);
                alert('There was an error loading the room type details.');
            }
        });
    } else {
        //alert("No room ID found. Please try again.");
    }

     
    $(document).on('click', '.remove-image-btn', function() {
        var imageUrl = $(this).data('url');  
        imagesToDelete.push(imageUrl);  

        $(this).parent().remove();  

         
        existingImages = existingImages.filter(function(img) {
            return img !== imageUrl;
        });
    });

     
    $('#updateRoomTypeForm').on('submit', function(event) {
        event.preventDefault();  

        //alert("meo meo");

        var formData = new FormData(this);

         
        formData.append('imagesToDelete', JSON.stringify(imagesToDelete));

         
        formData.append('existingImages', JSON.stringify(existingImages));

         
        var newImages = $('#imageUpload')[0].files;
        for (var i = 0; i < newImages.length; i++) {
            formData.append('newImages[]', newImages[i]);
        }


        alert(JSON.stringify(formData))
         
        $.ajax({
            url: "http://localhost:9999/roomType",  
            method: "PUT",
            data: formData,
            processData: false,  
            contentType: false,  
            success: function(response) {
                if (response.statusCode === 200) {
                    alert('Room type updated successfully!');
                    localStorage.removeItem('editRoomId');
                    window.location.href = "room_type_mana.html";
                } else {
                    alert('Failed to update room type: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Status: " + status);
                console.error("Error: " + error);
                console.error("Response: " + xhr.responseText);
                //alert('There was an error submitting the form.');
                //localStorage.removeItem('editRoomId');
                // window.location.href = "room_type_mana.html";
            }
        });
    });
});