package com.dsu.roomapi;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final InMemoryRoomRepository repository;
    private final RoomService roomService;

    public RoomController(InMemoryRoomRepository repository, RoomService roomService) {
        this.repository = repository;
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> list(@RequestParam(required = false) Integer minCapacity,
                           @RequestParam(defaultValue = "") String keyword) {
        return roomService.search(minCapacity, keyword);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findOne(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody RoomCreateRequest request) {
        Room room = repository.save(new Room(null, request.name(), request.capacity()));
        return ResponseEntity
                .created(URI.create("/api/rooms/" + room.id()))
                .body(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> replace(@PathVariable Long id,
                                        @RequestBody RoomCreateRequest request) {
        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Room updated = repository.save(
                new Room(id, request.name(), request.capacity())
        );

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = repository.deleteById(id);
        return removed ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
