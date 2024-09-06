$(document).ready(function () {
    showBooking()
    showStatus()
    showRooms()

    $('#submit_booking').click(function (e) {
        var inputBooking = {}

        inputBooking.firstName = $('#input_first_name').val();
        inputBooking.lastName = $('#input_last_name').val();
        inputBooking.phone = $('#input_phone').val();
        inputBooking.email = $('#input_email').val();
        inputBooking.address = $('#input_address').val();
        inputBooking.checkInDate = $('#input_checkin').val();
        inputBooking.checkOutDate = $('#input_checkout').val();
        inputBooking.rooms = $('#input_rooms').val();
        inputBooking.adultNumber = $('#input_adult').val();
        inputBooking.childrenNumber = $('#input_children').val();
        inputBooking.idBookingStatus = $('#input_booking_status').val();
        inputBooking.idPaymentStatus = $('#input_payment_status').val();
        inputBooking.idPayment = $('#input_payment_method').val();
        inputBooking.paidAmount = $('#input_paid_amount').val();
        inputBooking.total = $('#input_total').val();

        var idBooking = $('#booking_details').attr('idBooking')
        if (idBooking == "") {
            addBooking(inputBooking)
        } else {
            inputBooking.idBooking = idBooking
            updateBooking(inputBooking)
        }
    });

    $('#clear_booking').click(function (e) {
        e.preventDefault();
        clearAll()
    });

    $('body').on('click', '.update_booking', function (e) {
        e.preventDefault();
        var idBooking = $(this).attr("idBooking");
        showDetailBooking(idBooking)
        $('#booking_details').attr('idBooking', idBooking)
    });

    $('body').on('click', '.delete_booking', function (e) {
        e.preventDefault();

        var idBooking = $(this).attr("idBooking");

        if (confirm('Are you sure?')) {
            deleteBooking(idBooking)
        }
    });

});

function deleteBooking(id) {
    $.ajax({
        type: "DELETE",
        contentType: "application/json; charset=utf-8",
        url: `http://localhost:9999/booking/${id}`,
        success: function (response) {
            if (response.statusCode == 200) {
                alert(response.message)
            }

        }
    });
}

function updateBooking(inputEditBooking) {
    $.ajax({
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:9999/booking",
        data: JSON.stringify(inputEditBooking),
        success: function (response) {
            console.log(response)
            if (response.statusCode == 200) {
                return response.data
            }
        },
        error: function (response) {
            console.log(response)

            alert(response.responseJSON.message)
        }
    });
}

function showDetailBooking(id) {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: `http://localhost:9999/booking/${id}`,
        success: function (response) {
            if (response.statusCode == 200) {
                var booking = response.data
                $('#input_first_name').val(booking.firstName);
                $('#input_last_name').val(booking.lastName);
                $('#input_phone').val(booking.phone);
                $('#input_email').val(booking.email);
                $('#input_address').val(booking.address);
                $('#input_checkin').val(booking.checkIn);
                $('#input_checkout').val(booking.checkOut);
                $('#input_adult').val(booking.adultNo);
                $('#input_children').val(booking.childrenNo);
                $('#input_booking_status').val(booking.bookingStatus.id);
                $('#input_payment_status').val(booking.paymentStatus.id);
                $('#input_payment_method').val(booking.paymentMethod.id);
                $('#input_paid_amount').val(booking.paidAmount);
                $('#input_total').val(booking.total);

                var roomArr = []
                for (const key in booking.roomNo) {
                    roomArr = roomArr.concat(booking.roomNo[key])
                }


                $('#input_rooms').val(roomArr);
                $('#input_rooms').trigger("chosen:updated");

            }
            window.scrollTo({ top: 0, behavior: 'smooth' });

        }
    });
}

function clearAll() {
    $('input').val('');
    $('textarea').val('');
    $('#input_rooms').val('').trigger('chosen:updated');

    $('#booking_details').attr('idBooking', '')

}

function addBooking(inputAddBooking) {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:9999/booking",
        data: JSON.stringify(inputAddBooking),
        success: function (response) {
            if (response.statusCode == 200) {
                return response.data
            }
        },
        error: function (response) {
            alert(response.responseJSON.message)
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
                    <td class="${getClassOfStatus(item.bookingStatus.name)}">${item.bookingStatus.name}</td>
                    <td class="${getClassOfStatus(item.paymentStatus.name)}">${item.paymentStatus.name}</td>
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
                                <a idBooking="${item.id}" class="dropdown-item update_booking" href="#">Edit</a>
                                <a idBooking="${item.id}" class="dropdown-item delete_booking" href="#">Delete</a>
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

    for (const key in roomType) {

        let roomsString = ''
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