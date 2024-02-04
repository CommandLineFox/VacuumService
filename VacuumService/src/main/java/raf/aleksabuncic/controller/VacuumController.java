package raf.aleksabuncic.controller;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.constant.Permissions;
import raf.aleksabuncic.constant.Result;
import raf.aleksabuncic.dto.CreateVacuumCleanerDto;
import raf.aleksabuncic.dto.SearchDto;
import raf.aleksabuncic.dto.VacuumCleanerDto;
import raf.aleksabuncic.security.CheckSecurity;
import raf.aleksabuncic.security.service.TokenService;
import raf.aleksabuncic.service.VacuumService;

import java.util.List;

@RestController
@RequestMapping("/vacuum")
@CrossOrigin(origins = "http://localhost:4200")
public class VacuumController {
    private final VacuumService vacuumService;
    private final TokenService tokenService;

    public VacuumController(VacuumService vacuumService, TokenService tokenService) {
        this.vacuumService = vacuumService;
        this.tokenService = tokenService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<VacuumCleanerDto>> list(@RequestHeader("Authorization") String authorization) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(vacuumService.list(userId), HttpStatus.OK);
    }

    @PostMapping("/search")
    @CheckSecurity(roles = Permissions.searchVacuumPermission)
    public ResponseEntity<List<VacuumCleanerDto>> search(@RequestHeader("Authorization") String authorization, @RequestBody SearchDto searchDto) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(vacuumService.search(userId, searchDto), HttpStatus.OK);
    }

    @PutMapping("/add")
    @CheckSecurity(roles = Permissions.addVacuumPermission)
    public ResponseEntity<VacuumCleanerDto> add(@RequestHeader("Authorization") String authorization, @RequestBody CreateVacuumCleanerDto createVacuumCleanerDto) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(vacuumService.add(userId, createVacuumCleanerDto), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    @CheckSecurity(roles = Permissions.removeVacuumPermission)
    public ResponseEntity<VacuumCleanerDto> remove(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int result = vacuumService.rmeove(id, userId);
        if (result == Result.notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/start/{id}")
    @CheckSecurity(roles = Permissions.startVacuumPermission)
    public ResponseEntity<Boolean> start(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int result = vacuumService.start(id, userId);
        if (result == Result.notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (result == Result.badRequest) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/stop/{id}")
    @CheckSecurity(roles = Permissions.stopVacuumPermission)
    public ResponseEntity<Boolean> stop(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int result = vacuumService.stop(id, userId);
        if (result == Result.notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (result == Result.badRequest) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/discharge/{id}")
    @CheckSecurity(roles = Permissions.dischargeVacuumPermission)
    public ResponseEntity<Boolean> discharge(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int result = vacuumService.discharge(id, userId);
        if (result == Result.notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (result == Result.badRequest) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/start/{id}/{scheduledAction}")
    @CheckSecurity(roles = Permissions.startVacuumPermission)
    public ResponseEntity<Boolean> scheduleStart(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id, @PathVariable(value = "scheduledAction") long scheduledAction) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int result = vacuumService.scheduleStart(id, userId, scheduledAction);
        if (result == Result.notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (result == Result.badRequest) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/stop/{id}/{scheduledAction}")
    @CheckSecurity(roles = Permissions.stopVacuumPermission)
    public ResponseEntity<Boolean> scheduleStop(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id, @PathVariable(value = "scheduledAction") long scheduledAction) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int result = vacuumService.scheduleStop(id, userId, scheduledAction);
        if (result == Result.notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (result == Result.badRequest) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/discharge/{id}/{scheduledAction}")
    @CheckSecurity(roles = Permissions.dischargeVacuumPermission)
    public ResponseEntity<Boolean> scheduleDischarge(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id, @PathVariable(value = "scheduledAction") long scheduledAction) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int result = vacuumService.scheduleDischarge(id, userId, scheduledAction);
        if (result == Result.notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (result == Result.badRequest) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Long getUserId(String authorization) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get("id", Long.class);
    }
}
