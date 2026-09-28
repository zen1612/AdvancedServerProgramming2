package com.dsu.roomapi;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import aQute.bnd.annotation.plugin.InternalPluginNamespace;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Repository
public class InMemoryRoomRepository implements RoomRepository {
    private final List<Room> rooms = new ArrayList<>(List.of(
            new Room(1L, "Seminar A", 8),
            new Room(2L, "Study Pod", 4),
            new Room(3L, "Rooftop Room", 12)));

    private final AtomicLong nextId = new AtomicLong(4);

    public List<Room> findAll() {
        return List.copyOf(rooms);
    }

    public Optional<Room> findById(Long id){
        return rooms.stream()
                .filter(r -> r.id().equals(id))
                .findFirst();
    }

    public boolean deleteById(Long id){
        return rooms.removeIf(r -> r.id().equals(id));
    }

    public Room save (Room room){
        if (room.id() == null){
            Room created = new Room(nextId.getAndIncrement(), room.name(), room.capacity());
            rooms.add(created);
            return created;
        }
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).id().equals(room.id())) {
                rooms.set(i, room);
                return room;
            }
        }
        throw new IllegalArgumentException("No room id with " + room.id());
    }


}
