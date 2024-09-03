$(document).ready(function () {
    showBooking()


});

var showBooking = () => {
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

var convertRoomCol = (roomType) => {
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

var getClassOfStatus = (status) => {
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