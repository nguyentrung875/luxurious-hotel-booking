package com.java06.luxurious_hotel.service.imp;


import com.java06.luxurious_hotel.dto.RoomTypeDTO;
import com.java06.luxurious_hotel.entity.AmenityEntity;
import com.java06.luxurious_hotel.entity.BedTypeEntity;
import com.java06.luxurious_hotel.entity.RoomAmenityEntity;
import com.java06.luxurious_hotel.entity.RoomTypeEntity;
import com.java06.luxurious_hotel.entity.keys.RoomAmenityKey;
import com.java06.luxurious_hotel.repository.AmenityRepository;
import com.java06.luxurious_hotel.repository.RoomAmenityRepository;
import com.java06.luxurious_hotel.repository.RoomTypeRepository;
import com.java06.luxurious_hotel.request.AddRoomtypeRequest;
import com.java06.luxurious_hotel.request.UpdateRoomtypeRequest;
import com.java06.luxurious_hotel.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private RoomAmenityRepository roomAmenityRepository;

    @Transactional
    @Override
    public void addRoomType(AddRoomtypeRequest addRoomtypeRequest) {
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setName(addRoomtypeRequest.name());
        roomTypeEntity.setOverview(addRoomtypeRequest.overview());
        roomTypeEntity.setPrice(addRoomtypeRequest.price());
        roomTypeEntity.setArea(addRoomtypeRequest.area());
        roomTypeEntity.setCapacity(addRoomtypeRequest.capacity());

        BedTypeEntity bedTypeEntity = new BedTypeEntity();
        bedTypeEntity.setId(addRoomtypeRequest.iDBedType());

        roomTypeEntity.setBedType(bedTypeEntity);


        // lấy mọt list request image rồi dùng streamAPI đổi thành String rồi đưa vào database
        List<MultipartFile> listMFiles = addRoomtypeRequest.images();
        if (listMFiles != null && !listMFiles.isEmpty()) {
            String imagesString = addRoomtypeRequest.images().stream()
                    .map(MultipartFile::getOriginalFilename) // Extract the file names
                    .collect(Collectors.joining(","));
            roomTypeEntity.setImage(imagesString);
        }


        RoomTypeEntity roomType = roomTypeRepository.save(roomTypeEntity);

        addAmenitiesToRoomType(roomType.getId(), addRoomtypeRequest.idAmenity());


    }

    @Transactional
    @Override
    public boolean updateRoomType(UpdateRoomtypeRequest updateRoomtypeRequest) {

        boolean result = false;

        Optional<RoomTypeEntity> roomTypeEntityCheck = roomTypeRepository.findById(updateRoomtypeRequest.id());
        if (roomTypeEntityCheck.isPresent()) {
            RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
            roomTypeEntity.setId(updateRoomtypeRequest.id());
            roomTypeEntity.setName(updateRoomtypeRequest.name());
            roomTypeEntity.setOverview(updateRoomtypeRequest.overview());
            roomTypeEntity.setPrice(updateRoomtypeRequest.price());
            roomTypeEntity.setArea(updateRoomtypeRequest.area());
            roomTypeEntity.setCapacity(updateRoomtypeRequest.capacity());

            BedTypeEntity bedTypeEntity = new BedTypeEntity();
            bedTypeEntity.setId(updateRoomtypeRequest.iDBedType());

            roomTypeEntity.setBedType(bedTypeEntity);


            List<MultipartFile> listMFiles = updateRoomtypeRequest.images();
            if (listMFiles != null && !listMFiles.isEmpty()) {
                String imagesString = updateRoomtypeRequest.images().stream()
                        .map(MultipartFile::getOriginalFilename) // Extract the file names
                        .collect(Collectors.joining(","));
                roomTypeEntity.setImage(imagesString);
            }

            RoomTypeEntity roomType = roomTypeRepository.save(roomTypeEntity);
            roomAmenityRepository.deleteByRoomTypeId(updateRoomtypeRequest.id());
            addAmenitiesToRoomType(roomType.getId(), updateRoomtypeRequest.idAmenity());

            result = true;

        }



        return result;
    }


    @Override
    public List<RoomTypeDTO> allRoomTypes() {
        List<RoomTypeEntity> roomTypeEntityList = roomTypeRepository.findAll();
        List<RoomTypeDTO> roomTypeDTOList = new ArrayList<>();
        for (RoomTypeEntity roomTypeEntity : roomTypeEntityList) {
            RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
            roomTypeDTO.setId(roomTypeEntity.getId());
            roomTypeDTO.setName(roomTypeEntity.getName());
            roomTypeDTO.setOverview(roomTypeEntity.getOverview());
            roomTypeDTO.setPrice(roomTypeEntity.getPrice());
            roomTypeDTO.setArea(roomTypeEntity.getArea());
            roomTypeDTO.setCapacity(roomTypeEntity.getCapacity());
            roomTypeDTO.setBedName(roomTypeEntity.getBedType().getName());
            String imagesString = roomTypeEntity.getImage();
            if (imagesString != null && !imagesString.isEmpty()) {
                List<String> imagesList = Arrays.stream(imagesString.split(","))
                        .collect(Collectors.toList());
                roomTypeDTO.setImage(imagesList);
            }

            List<RoomAmenityEntity> list = roomAmenityRepository.findByRoomTypeId(roomTypeEntity.getId());
            String amenityStr = list.stream().
                    map(roomAmenityEntity -> roomAmenityEntity.getAmenity().getDescription()).
                    collect(Collectors.joining(", "));

            roomTypeDTO.setAmenity(amenityStr);
            //          for (RoomAmenityEntity roomAmenityEntity : list) {
//              amenityStr+= (roomAmenityEntity.getAmenity().getDescription()+", ");
//          }
            roomTypeDTOList.add(roomTypeDTO);
        }
        return roomTypeDTOList;
    }

    @Transactional
    @Override
    public RoomTypeDTO findRoomTypeById(int id) {

        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setId(roomTypeEntity.getId());
        roomTypeDTO.setName(roomTypeEntity.getName());
        roomTypeDTO.setOverview(roomTypeEntity.getOverview());
        roomTypeDTO.setPrice(roomTypeEntity.getPrice());
        roomTypeDTO.setArea(roomTypeEntity.getArea());
        roomTypeDTO.setCapacity(roomTypeEntity.getCapacity());
        roomTypeDTO.setBedName(roomTypeEntity.getBedType().getName());
        String imagesString = roomTypeEntity.getImage();

        if (imagesString != null && !imagesString.isEmpty()) {
            List<String> imagesList = Arrays.stream(imagesString.split(","))
                    .collect(Collectors.toList());
            roomTypeDTO.setImage(imagesList);
        }


        List<RoomAmenityEntity> list = roomAmenityRepository.findByRoomTypeId(roomTypeEntity.getId());
        String amenityStr = list.stream().
                map(roomAmenityEntity -> roomAmenityEntity.getAmenity().getDescription()).
                collect(Collectors.joining(", "));

        roomTypeDTO.setAmenity(amenityStr);
        return roomTypeDTO;
    }


    @Transactional
    @Override
    public boolean deleteRoomTypeById(int id) {

        boolean isSuccess = false;

        Optional<RoomTypeEntity> roomTypeOptional = roomTypeRepository.findById(id);

        if (roomTypeOptional.isPresent()) {
            roomAmenityRepository.deleteByRoomTypeId(id);
            roomTypeRepository.deleteById(id);

            isSuccess = true;
        }

        return isSuccess;
    }




    public void addAmenitiesToRoomType(int roomTypeId, List<Integer> idAmenity) {
        // Lấy RoomType dựa trên roomTypeId
        RoomTypeEntity roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        // Duyệt qua từng idAmenity trong danh sách
        for (Integer amenityId : idAmenity) {
            AmenityEntity amenity = amenityRepository.findById(amenityId)
                    .orElseThrow(() -> new RuntimeException("Amenity not found"));

            // Tạo đối tượng RoomAmenityEntity và lưu vào DB
            RoomAmenityKey key = new RoomAmenityKey(roomType.getId(), amenity.getId());
            RoomAmenityEntity roomAmenity = new RoomAmenityEntity();
            roomAmenity.setRoomAmenityKey(key);
            roomAmenity.setRoomType(roomType);
            roomAmenity.setAmenity(amenity);

            roomAmenityRepository.save(roomAmenity);
        }
    }


}
