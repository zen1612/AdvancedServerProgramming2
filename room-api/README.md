# room-api — Week 3 starter

Starting point for Week 4. This is the Week 3 Room API with all five checkpoints done,
kept in a single controller on purpose. In Week 4 we split it into layers.

## Requirements

- JDK 21

## Run

```
./gradlew bootRun          # macOS / Linux
gradlew.bat bootRun        # Windows
```

The server starts on http://localhost:8080. Run the requests in `api.http` from top to bottom.

## Endpoints

| Method | Path              | Status    | Description                                          |
|--------|-------------------|-----------|------------------------------------------------------|
| GET    | `/api/rooms`      | 200       | All rooms; optional `minCapacity` and `keyword`       |
| GET    | `/api/rooms/{id}` | 200 / 404 | One room                                             |
| POST   | `/api/rooms`      | 201       | Create a room; `Location` header and the new room    |
| PUT    | `/api/rooms/{id}` | 200 / 404 | Replace a room                                       |
| DELETE | `/api/rooms/{id}` | 204 / 404 | Delete a room                                        |
