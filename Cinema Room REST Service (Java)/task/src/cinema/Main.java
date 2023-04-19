package cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Main {
    private static List<Map<String, Object>> availableSeats;

    public static void main(String[] args) {
        availableSeats = new ArrayList<>();

        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                Map<String, Object> seat = new HashMap<>();
                seat.put("row", row);
                seat.put("column", column);
                if (row <= 4) {
                    seat.put("price", 10);
                } else {
                    seat.put("price", 8);
                }
               seat.put("purchased", false);

                availableSeats.add(seat);
            }
        }
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/seats")
    public synchronized ResponseEntity<Map<String, Object>> getSeats() {
        Map<String, Object> response = new HashMap<>();
        response.put("total_rows", 9);
        response.put("total_columns", 9);
        List<Map<String, Object>> seats = new ArrayList<>();
        for (Map<String, Object> seat : availableSeats) {
            Map<String, Object> seatInfo = new HashMap<>();
            seatInfo.put("row", seat.get("row"));
            seatInfo.put("column", seat.get("column"));
            seatInfo.put("price", seat.get("price"));
            seatInfo.put("purchased", seat.get("purchased"));
            seats.add(seatInfo);
        }
        response.put("available_seats", seats);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/purchase")
    public synchronized ResponseEntity<?> purchaseSeat(@RequestBody Map<String, Integer> request) {
            int row = request.get("row");
            int column = request.get("column");

            if (row < 1 || row > 9 || column < 1 || column > 9) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "The number of a row or a column is out of bounds!");
                return ResponseEntity.badRequest().body(error);
            }

            Map<String, Object> seat = availableSeats.get((row - 1) * 9 + column - 1);

            if ((Boolean) seat.get("purchased")) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "The ticket has been already purchased!");
                return ResponseEntity.badRequest().body(error);
            }

            seat.put("purchased", true);
            int price = (Integer) seat.get("price");
            Map<String, Object> response = new HashMap<>();
            response.put("row", row);
            response.put("column", column);
            response.put("price", price);
            return ResponseEntity.ok(response);
        }
    }
