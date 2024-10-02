$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const employeeId = urlParams.get('id');

    // Lấy danh sách role từ API và đổ vào dropdown
    function loadRoles(selectedRoleId) {
        $.ajax({
            url: 'http://localhost:9999/role/allrole',  // API lấy danh sách các role
            method: 'GET',
            success: function(response) {
                if (response.statusCode === 200) {
                    let roles = response.data;
                    let roleDropdown = $('#employee-role');
                    roleDropdown.empty();  // Xóa các option cũ
                    roleDropdown.append(`<option value="">-- Select Role --</option>`);

                    $.each(roles, function(index, role) {
                        let selected = role.id === selectedRoleId ? 'selected' : '';
                        roleDropdown.append(`<option value="${role.id}" ${selected}>${role.name}</option>`);
                    });
                } else {
                    console.error('Failed to fetch roles');
                }
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }

    // Gọi API để lấy thông tin hiện tại của nhân viên
    if (employeeId) {
        $.ajax({
            url: `http://localhost:9999/employee/${employeeId}`,  // API lấy thông tin nhân viên
            method: 'GET',
            success: function(response) {
                if (response.statusCode === 200) {
                    let employee = response.data;

                    // Đổ dữ liệu vào form
                    $('#employee-firstname').val(employee.firstname);
                    $('#employee-lastname').val(employee.lastname);
                    $('#employee-dob').val(employee.dob);
                    $('#employee-phone').val(employee.phone);
                    $('#employee-email').val(employee.email);
                    $('#employee-address').val(employee.address);
                    $('#employee-summary').val(employee.summary);

                    // Gọi hàm loadRoles và đổ dữ liệu role vào dropdown (với role hiện tại của nhân viên)
                    loadRoles(employee.roleId);  // Đổ role hiện tại của nhân viên
                } else {
                    console.error('Failed to fetch employee details');
                }
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }

    // Xử lý khi nhấn nút "Save Changes"
    $('#userUpdate').click(function(e) {
        e.preventDefault();  // Ngăn chặn form submit mặc định

        // Kiểm tra nếu roleId chưa được chọn
        if (!$('#employee-role').val()) {
            alert('Please select a role');
            return;
        }

        // Tạo đối tượng FormData để upload file hình ảnh cùng các dữ liệu khác
        let formData = new FormData();
        formData.append('id', employeeId);  // Thêm ID nhân viên vào FormData
        formData.append('firstname', $('#employee-firstname').val());
        formData.append('lastname', $('#employee-lastname').val());
        formData.append('dob', $('#employee-dob').val());
        formData.append('phone', $('#employee-phone').val());
        formData.append('email', $('#employee-email').val());
        formData.append('address', $('#employee-address').val());
        formData.append('summary', $('#employee-summary').val());
        formData.append('roleId', $('#employee-role').val());

        // Lấy file ảnh nếu có
        let imageInput = $('#imageUpload')[0];
        if (imageInput && imageInput.files && imageInput.files.length > 0) {
            let imageFile = imageInput.files[0];
            formData.append('imageUpload', imageFile);  // Thêm hình ảnh vào FormData
        } else {
            console.log('No image file selected');
        }
        console.log (formData)
        // Gửi yêu cầu cập nhật thông tin và hình ảnh của nhân viên
        $.ajax({
            url: 'http://localhost:9999/employee/${employeeId}',  // API cập nhật thông tin nhân viên
            method: 'PUT',
            contentType: false,  // Không đặt kiểu nội dung để FormData tự xử lý
            processData: false,  // Không xử lý dữ liệu thành chuỗi
            data: formData,  // Gửi FormData bao gồm cả hình ảnh
            success: function(response) {
                if (response.statusCode === 200) {
                    alert('Employee updated successfully');
                    window.location.href = 'team-list.html';  // Chuyển hướng sau khi cập nhật thành công
                } else {
                    alert('Failed to update employee');
                }
            },
            error: function(error) {
                console.error('Error:', error);
                alert('Error: Unable to update employee');
            }
        });
    });
});
