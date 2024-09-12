$(document).ready(function () {
        // Dummy booking data
        const bookings = [
            {
                id: 1,
                checkIn: '2024-09-10',
                checkOut: '2024-09-15',
                roomType: 'Deluxe Room',
                roomNumber: '101',
                guests: 2,
                bookingDate: '2024-08-25',
                paymentStatus: 'Paid',
                deposit: 100,
                totalAmount: 500,
                bookingStatus: 'Confirmed'
            },
            {
                id: 2,
                checkIn: '2024-09-20',
                checkOut: '2024-09-22',
                roomType: 'Standard Room',
                roomNumber: '202',
                guests: 1,
                bookingDate: '2024-08-28',
                paymentStatus: 'Pending',
                deposit: 50,
                totalAmount: 300,
                bookingStatus: 'Pending'
            },
        ];

        // Function to render bookings
        function renderBookings() {
            const bookingHistory = $('#bookingHistory');
            bookingHistory.empty(); // Clear any existing content

            bookings.forEach(booking => {
                const bookingItem = `
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="heading${booking.id}">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${booking.id}" aria-expanded="true" aria-controls="collapse${booking.id}">
                                <span class="left">${booking.checkIn} - ${booking.checkOut}</span>
                                <span class="right">${booking.bookingStatus}</span>
                            </button>
                        </h2>
                        <div id="collapse${booking.id}" class="accordion-collapse collapse" aria-labelledby="heading${booking.id}" data-bs-parent="#bookingHistory">
                            <div class="accordion-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <ul class="list-group">
                                            <li class="list-group-item"><strong>Booking ID:</strong> ${booking.id}</li>
                                            <li class="list-group-item"><strong>Check-in:</strong> ${booking.checkIn}</li>
                                            <li class="list-group-item"><strong>Check-out:</strong> ${booking.checkOut}</li>
                                            <li class="list-group-item"><strong>Room Type/Number:</strong> ${booking.roomType} / ${booking.roomNumber}</li>
                                        </ul>
                                    </div>
                                    <div class="col-md-6">
                                        <ul class="list-group">
                                            <li class="list-group-item"><strong>Number of Guests:</strong> ${booking.guests}</li>
                                            <li class="list-group-item"><strong>Booking Date:</strong> ${booking.bookingDate}</li>
                                            <li class="list-group-item"><strong>Payment Status:</strong> ${booking.paymentStatus}</li>
                                            <li class="list-group-item"><strong>Deposit:</strong> $${booking.deposit}</li>
                                            <li class="list-group-item"><strong>Total Amount:</strong> $${booking.totalAmount}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                bookingHistory.append(bookingItem);
            });
        }

        // Function to search booking by phone number (currently demo)
        function searchBooking() {
            const searchPhone = $('#searchPhone').val();
            // Logic to filter bookings by phone number can go here
            console.log('Searching bookings for phone number:', searchPhone);
            renderBookings(); // Re-render the booking list (should filter in real case)
        }

        // Render bookings on page load
        $(document).ready(function () {
            renderBookings();

            // Add search event listener
            $('#searchPhone').on('input', function () {
                searchBooking();
            });
        });
});