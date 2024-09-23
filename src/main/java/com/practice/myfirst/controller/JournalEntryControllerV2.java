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


//        @GetMapping
//        public List<JournalEntry > getAll(){
//          return journalEntryService.getAll();
//        }

    @GetMapping
    public List<JournalEntry > getAll() {
//        List<JournalEntry> entries = journalEntryService.getAll();
            return journalEntryService.getAll();// Return 204 No Content if the list is empty
    }




    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

//    @GetMapping("/id/{myid}")
//    public JournalEntry getJournalEntryById(@PathVariable ObjectId myid){
//        return journalEntryService.findById(myid).orElse(null);
//    }
@GetMapping("/id/{myid}")
public JournalEntry getJournalEntryById(@PathVariable ObjectId myid) {

    return journalEntryService.findById(myid).orElse(null);
}


    @DeleteMapping("/id/{myid}")
    public Boolean deleteJournalEntryById(@PathVariable ObjectId myid){
        journalEntryService.deleteById(myid);
        return true;
    }

//    @DeleteMapping("/id/{myid}")
//    public Boolean deleteJournalEntryById(@PathVariable ObjectId myid) {
//        // Check if the entry exists before attempting deletion
//
//            journalEntryService.deleteById(myid);
//            return true;
//
//    }



//    @PutMapping("/id/{myid}")
//    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myid,@RequestBody JournalEntry newEntry){
//     JournalEntry old =    journalEntryService.findById(myid).orElse(null);
//     if(old != null){
//    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle():old.getTitle());
//    old.setContent(newEntry.getContent()!=null && !newEntry.equals("")? newEntry.getContent(): old.getContent());
//     }
//        journalEntryService.saveEntry(old);
//        return old;
//    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);

        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.equals("")? newEntry.getContent(): old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;

    }


}