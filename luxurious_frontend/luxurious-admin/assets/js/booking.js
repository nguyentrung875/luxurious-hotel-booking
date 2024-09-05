$(document).ready(function () {
    showBooking()
    showStatus()
    showRooms()
    
    $('#submit_booking').click(function (e) { 
        $('#liveToast').show();

        var inputAddBooking = {
            "firstName": "Thanh",
            "lastName": "Nguyễn",
            "phone": "0931789386",
            "email": "nguyenkiem12@gmail.com",
            "address": "123 Cao Thắng, P5, Q3, TPHCM",
            "checkInDate": "2025-03-20",
            "checkOutDate": "2025-03-25",
            "roomNumber": 4,
            "rooms": "20",
            "adultNumber": 8,
            "childrenNumber": 1,
            "idBookingStatus" : 2,
            "idPaymentStatus": 1,
            "idPayment": 1,
            "paidAmount": 20.5,
            "total": 50.0
        }

        inputAddBooking.firstName = $('#input_first_name').val();
        inputAddBooking.lastName = $('#input_last_name').val();
        inputAddBooking.phone = $('#input_phone').val();
        inputAddBooking.email = $('#input_email').val();
        inputAddBooking.checkInDate = $('#input_checkin').val();
        inputAddBooking.checkOutDate = $('#input_checkout').val();
        inputAddBooking.rooms = $('#input_rooms').val();
        inputAddBooking.adultNumber = $('#input_adult').val();
        inputAddBooking.childrenNumber = $('#input_children').val();
        inputAddBooking.idBookingStatus = $('#input_booking_status').val();
        inputAddBooking.idPaymentStatus = $('#input_payment_status').val();
        inputAddBooking.idPayment = $('#input_payment_method').val();
        inputAddBooking.paidAmount = $('#input_paid_amount').val();
        inputAddBooking.total = $('#input_total').val();
        
        console.log(inputAddBooking)

        addBooking(inputAddBooking)
    });
});



var addBooking = (inputAddBooking) => {
    console.log(inputAddBooking)

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:9999/booking",
        data: JSON.stringify(inputAddBooking),
        success: function (response) {
            console.log(response)
        },
        error: function(response){
            console.log('error ajax',response.responseJSON)
        }
    });

}

function showRooms() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:9999/roomType",
        success: function (response) {
            html = ''
            for (let i = 0; i < response.data.length; i++) {
                let itemRoomType = response.data[i]
                html += `<optgroup label="${itemRoomType.name} (${itemRoomType.price}$/night)">`
                for (let j = 0; j < itemRoomType.roomName.length; j++) {
                    let roomName = itemRoomType.roomName[j]
                    html += `<option disable="disable" value="${roomName}">${roomName}</option>`
                }
                html += `</optgroup>`
            }

            $('#input_rooms').append(html);
            $('#input_rooms').trigger("chosen:updated");

        }
    });
}

function showStatus() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:9999/status",
        success: function (response) {
            

            response.data.listPaymentStatus.forEach(item => {
                $("#input_payment_status").append(`<option value="${item.id}">${item.name}</option>`);
            });

            response.data.listPaymentMethod.forEach(item => {
                $("#input_payment_method").append(`<option value="${item.id}">${item.name}</option>`);
            });

            response.data.listBookingStatus.forEach(item => {
                $("#input_booking_status").append(`<option value="${item.id}">${item.name}</option>`);
            });
        }
    });
}

function showBooking(params) {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:9999/booking",
        success: function (response) {

            var dt = $('#booking_table').DataTable();
            dt.destroy();

            for (let i = 0; i < response.data.length; i++) {
                var item = response.data[i]
                var htmlRow =
                    `<tr>
                    <td class="token">${item.id}</td>
                    <td><span class="name">${item.firstName} ${item.lastName}</span>
                    </td>
                    <td>${item.checkIn}</td>
                    <td>${item.checkOut}</td>
                    <td class="${getClassOfStatus(item.bookingStatus)}">${item.bookingStatus}</td>
                    <td class="${getClassOfStatus(item.paymentStatus)}">${item.paymentStatus}</td>
                    <td>$${item.paidAmount}</td>
                    <td><b>$${item.total}</b></td>
                    <td class="type">${convertRoomCol(item.roomNo)}</td>
                    <td class="rooms">
                        <span class="mem">${item.adultNo} Adult</span> /
                        <span class="room">${item.childrenNo} Children</span>
                    </td>
                    <td>
                        <div class="d-flex justify-content-center">
                            <button type="button" class="btn btn-outline-success"><i
                                    class="ri-information-line"></i></button>
                            <button type="button"
                                class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                data-bs-toggle="dropdown" aria-haspopup="true"
                                aria-expanded="false" data-display="static">
                                <span class="sr-only"><i
                                        class="ri-settings-3-line"></i></span>
                            </button>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">Edit</a>
                                <a class="dropdown-item" href="#">Delete</a>
                            </div>
                        </div>
                    </td>
                </tr>`

                $('#booking_table tbody').append(htmlRow);
            }

            $('#booking_table').DataTable();
        }
    });
}

function convertRoomCol(roomType) {
    let roomNo = ''
    let roomsString = ''

    for (const key in roomType) {

        roomType[key].forEach(item => {
            roomsString += item + ', '
        });
        roomNo += `<span>${key} : </span>${roomsString}`
    }
    return roomNo.substring(roomNo, roomNo.length - 2)
}


function getClassOfStatus(status) {
    let className = ''
    switch (status) {
        case 'Pending Confirmation':
            className = 'pending'
            break;
        case 'Confirmed':
            className = 'confirmed'
            break;
        case 'Checked In':
            className = 'checked_in'
            break;
        case 'Checked Out':
            className = 'checked_out'
            break;
        case 'Cancelled':
            className = 'cancelled'
            break;
        case 'Pending':
            className = 'pending'
            break;
        case 'Completed':
            className = 'completed'
            break;
        case 'Failed':
            className = 'failed'
            break;
        case 'Refunded':
            className = 'refunded'
            break;
        case 'Partially Paid':
            className = 'partially_paid'
            break;
        case 'cancelled':
            className = 'cancelled'
            break;
        case 'awaiting_confirmation':
            className = 'awaiting_confirmation'
            break;
        default:
            break;
    }
    return className;
}