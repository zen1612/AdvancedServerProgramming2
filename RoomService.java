package com.dsu.roomapi;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> search(Integer minCapacity, String keyword) {
        if (minCapacity == null){
            return roomRepository.findByNameContainingIgnoreCase(keyword);
        }
        return roomRepository.findByCapacityGreaterThanEqualAndNameContainingIgnoreCase(minCapacity, keyword);
        /*return roomRepository.findAll().stream()
                .filter(r -> minCapacity == null || r.getCapacity() >= minCapacity)
                .filter(r -> r.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();*/
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public Room create(String name, int capacity) {
        checkCapacity(capacity);
        return roomRepository.save(new Room(null, name, capacity));
    }

    public Optional<Room> replace(Long id, String name, int capacity) {
        checkCapacity(capacity);
        return roomRepository.findById(id)
                .map(existing -> roomRepository.save(new Room(id, name, capacity)));
    }

    public boolean delete(Long id){
        if (!roomRepository.existsById(id)) {
            return false;
        }
        roomRepository.deleteById(id);
        return true;
    }

    public int count() {
        return roomRepository.findAll().size();
    }

    private void checkCapacity(int capacity) {
        if (capacity < 1 || capacity > 20) {
            throw new IllegalArgumentException("capacity must be between 1 and 20");
        }
    }
}
