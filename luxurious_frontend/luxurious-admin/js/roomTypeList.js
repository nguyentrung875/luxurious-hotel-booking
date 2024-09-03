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
														<td>${item.amenity}</td>
														
														
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
																	<a class="dropdown-item" href="RoomTypeAddEdit.html">Edit</a>
																	<a class="dropdown-item" href="#">Delete</a>
																</div>
															</div>
														</td>
													</tr>`
            }
            $('#list-roomtype').append(html)
          }
    });
})