package com.practice.myfirst.controller;
import com.practice.myfirst.entity.JournalEntry;
import com.practice.myfirst.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public ResponseEntity<?> getAll() {
       List<JournalEntry> all = journalEntryService.getAll();
       if(all !=null && !all.isEmpty()){
           return new ResponseEntity<>(all,HttpStatus.OK);
       }
       return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
  try {
      myEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(myEntry);
      return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
  } catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
    }

@GetMapping("/id/{myid}")
public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid) {
    Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
    if(journalEntry.isPresent()){
        return new  ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
    }
    return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
}


    @DeleteMapping("/id/{myid}")
    public  ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid){
        journalEntryService.deleteById(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);

        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.equals("")? newEntry.getContent(): old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}