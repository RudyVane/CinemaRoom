package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private Room room = new Room(9, 9);

    @GetMapping("/seats")
    public ResponseEntity<Room> getSeats() {
        return ResponseEntity.ok(room);
    }

    private static class Room {
        private final int total_rows;
        private final int total_columns;
        private final Seat[] available_seats;

        public Room(int total_rows, int total_columns) {
            this.total_rows = total_rows;
            this.total_columns = total_columns;
            this.available_seats = new Seat[total_rows * total_columns];

            int i = 0;
            for (int row = 1; row <= total_rows; row++) {
                for (int col = 1; col <= total_columns; col++) {
                    available_seats[i] = new Seat(row, col);
                    i++;
                }
            }
        }

        public int getTotal_rows() {
            return total_rows;
        }

        public int getTotal_columns() {
            return total_columns;
        }

        public Seat[] getAvailable_seats() {
            return available_seats;
        }
    }

    private static class Seat {
        private final int row;
        private final int column;

        public Seat(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}
