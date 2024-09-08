$(document).ready(function () {
    
    getGuest()


});

function getGuest(){
    $.ajax({
        url: "http://localhost:9999/user/guests",
        method: "POST"
    }).done(function( response){
        if(response.data){
            var html = ""
            for(i=0;i<response.data.length;i++){

                var item = response.data[i]
                html += `<tr>
													<td class="token">${item.id}</td>
													<td><img class="cat-thumb" src="assets/img/user/1.jpg"
															alt="clients Image"><span class="name">${item.fullName}</span>
													</td>
													<td>${item.phone}</td>
													<td>${item.email}</td>
													<td>${item.address}</td>
													<td>${item.summary}</td>
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
																<a class="dropdown-item" href="guest-update.html?id=${item.id}">Edit</a>
																<a class="delete-btn dropdown-item" href="#?id=${item.id}">Delete</a>
															</div>
														</div>
													</td>
												</tr>`

            }
            $('#list-guests').append(html)
        }

    })
}