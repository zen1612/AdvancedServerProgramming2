package com.dsu.roomapi;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final InMemoryRoomRepository roomRepository;

    public RoomService(InMemoryRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> search(Integer minCapacity, String keyword) {
        return roomRepository.findAll().stream()
                .filter(r -> minCapacity == null || r.capacity() >= minCapacity)
                .filter(r -> r.name().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
    public Room create (String name, int capacity){
        return roomRepository.save(new Room(null, name, capacity));
    }

    public Optional<Room> replace(Long id, String name, int capacity){
        return roomRepository.findById(id)
                .map(old->roomRepository.save(new Room(id, name, capacity)));
    }
}
