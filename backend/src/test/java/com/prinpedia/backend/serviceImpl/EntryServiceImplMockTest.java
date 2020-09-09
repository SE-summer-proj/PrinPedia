package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.dao.EntryRelationDao;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.EntryEditRequest;
import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.repository.EntryEditRequestRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class EntryServiceImplMockTest {
    @Mock
    private EntryRelationDao entryRelationDao;

    @Mock
    private EntryDao entryDao;

    @Mock
    private EntryEditRequestRepository entryEditRequestRepository;

    @InjectMocks
    private EntryServiceImpl entryService = new EntryServiceImpl();

    @DisplayName("Find an entry by title")
    @Test
    public void findByTitle() {
        Entry entry = new Entry();
        entry.setTitle("Test");
        Optional<Entry> optionalEntry = Optional.of(entry);
        Mockito.when(entryDao.findByTitle("Test")).thenReturn(optionalEntry);
        Optional<Entry> optionalEntry1 = Optional.empty();
        Mockito.when(entryDao.findByTitle("123")).thenReturn(optionalEntry1);

        String title = "Test";

        Entry result = entryService.findByTitle(title);
        assertNotNull(result, "Cannot find entry by title");
        assertEquals(title, result.getTitle(), "Titles don't match");

        title = "123";

        result = entryService.findByTitle(title);
        assertNull(result, "Find an entry which shouldn't exist");
    }

    @Test
    public void createEntry() {
        String dup = "duplicate";
        String create = "create";

        Entry entry = new Entry();
        entry.setTitle(dup);
        Optional<Entry> entryOptional = Optional.of(entry);
        Mockito.when(entryDao.findByTitle(dup)).thenReturn(entryOptional);
        Optional<Entry> entryOptional1 = Optional.empty();
        Mockito.when(entryDao.findByTitle(create))
                .thenReturn(entryOptional1);
        Mockito.when(entryDao.create(Mockito.any(Entry.class))).thenReturn(true);

        Boolean result = entryService.createEntry(dup);
        assertFalse(result, "Created duplicate entry");

        result = entryService.createEntry(create);
        assertTrue(result, "Create entry failure");
    }

    @Test
    public void editEntry() {
        String edit = "edit";
        String emptyEdit = "empty";
        String wiki = "wiki";

        Entry entry = new Entry();
        entry.setTitle(edit);
        Optional<Entry> entryOptional = Optional.of(entry);
        Mockito.when(entryDao.findByTitle(edit)).thenReturn(entryOptional);
        Optional<Entry> entryOptional1 = Optional.empty();
        Mockito.when(entryDao.findByTitle(emptyEdit)).thenReturn(entryOptional1);
        Mockito.when(entryDao.update(Mockito.any(Entry.class))).thenReturn(true);

        Boolean result = entryService.editEntry(edit, wiki);
        assertTrue(result, "Can't edit entry");

        result = entryService.editEntry(emptyEdit, wiki);
        assertTrue(result, "Can't create new entry through edit");
    }

    @Test
    public void editEntryRequest() {
        EntryEditRequest entryEditRequest = new EntryEditRequest();
        String title = "test";
        String wikiText = "wiki";
        String username = "test";
        Mockito.when(entryEditRequestRepository.
                save(Mockito.any(EntryEditRequest.class)))
                .thenReturn(entryEditRequest);

        Boolean result =
                entryService.editEntryRequest(title, wikiText, username);
        assertTrue(result, "Edit entry request failure");
    }

    @Test
    public void getEditLog() {
        Mockito.when(entryEditRequestRepository
                .findByUsername(Mockito.anyString()))
                .thenReturn(null);
        Mockito.when(entryEditRequestRepository
                .findByStatusGreaterThan(Mockito.anyInt()))
                .thenReturn(null);
        Mockito.when(entryEditRequestRepository
                .findByStatus(Mockito.anyInt()))
                .thenReturn(null);
        EntryEditRequest entryEditRequest = new EntryEditRequest();
        Optional<EntryEditRequest> entryEditRequestOptional
                = Optional.of(entryEditRequest);
        Optional<EntryEditRequest> empty = Optional.empty();
        ObjectId id1 = new ObjectId("5f06b9d4643cd113ed90bc5a");
        ObjectId id2 = new ObjectId("5f06b9d5643cd113ed90bc5b");
        Mockito.when(entryEditRequestRepository.findById(id1))
                .thenReturn(entryEditRequestOptional);
        Mockito.when(entryEditRequestRepository.findById(id2))
                .thenReturn(empty);

        entryService.getEditLogByUser("test");
        entryService.getEditLogAdmin(true);
        entryService.getEditLogAdmin(false);
        EntryEditRequest result = entryService.getEditLogById(id1);
        assertEquals(entryEditRequest, result, "Don't match");
        result = entryService.getEditLogById(id2);
        assertNull(result, "Don't match");
    }

    @Test
    public void examine() {
        Optional<EntryEditRequest> optional1 = Optional.empty();
        ObjectId id1 = new ObjectId("5f06b9d4643cd113ed90bc5a");
        Mockito.when(entryEditRequestRepository.findById(id1))
                .thenReturn(optional1);
        EntryEditRequest request1 = new EntryEditRequest();
        request1.setStatus(1);
        Optional<EntryEditRequest> optional2 = Optional.of(request1);
        ObjectId id2 = new ObjectId("5f06b9d5643cd113ed90bc5b");
        Mockito.when(entryEditRequestRepository.findById(id2))
                .thenReturn(optional2);
        Mockito.when(entryEditRequestRepository
                .save(Mockito.any(EntryEditRequest.class)))
                .thenReturn(null);
        EntryEditRequest request2 = new EntryEditRequest();
        request2.setStatus(0);
        String title = "edit";
        request2.setTitle(title);
        request2.setWikiText("wiki");
        Optional<EntryEditRequest> optional3 = Optional.of(request2);
        ObjectId id3 = new ObjectId("5f06b9d5643cd113ed90bc5c");
        Mockito.when(entryEditRequestRepository.findById(id3))
                .thenReturn(optional3);
        Entry entry = new Entry();
        entry.setTitle(title);
        Optional<Entry> entryOptional = Optional.of(entry);
        Mockito.when(entryDao.findByTitle(title)).thenReturn(entryOptional);
        Mockito.when(entryDao.update(Mockito.any(Entry.class))).thenReturn(true);

        Boolean result = entryService.examineEditLog(id1, true);
        assertFalse(result,
                "Examination succeeds even with wrong id");

        result = entryService.examineEditLog(id2, true);
        assertFalse(result,
                "Examination succeeds even when already examined ");

        result = entryService.examineEditLog(id3, true);
        assertTrue(result, "Pass edit request failure");

        request2.setStatus(0);
        result = entryService.examineEditLog(id3, false);
        assertTrue(result, "Reject edit request failure");
    }

    @DisplayName("Find an entry's parents and children")
    @Test
    public void findParentsAndChildren() {
        EntryNode node1 = new EntryNode();
        node1.setIndex(1);
        node1.setTitle("first");
        EntryNode node2 = new EntryNode();
        node2.setIndex(2);
        node2.setTitle("second");

        List<EntryNode> entryNodeList = new ArrayList<>();
        entryNodeList.add(node1);
        entryNodeList.add(node2);

        Mockito.when(entryRelationDao.findParents(Mockito.anyString()))
                .thenReturn(entryNodeList);
        Mockito.when(entryRelationDao.findChildren(Mockito.anyString()))
                .thenReturn(entryNodeList);

        List<String> result = entryService.findParents("title");
        assertNotNull(result, "Cannot get result");
        assertEquals(2, result.size(), "Result size don't match");
        assertTrue(result.contains("first"),
                "Cannot find first parent entry title");
        assertTrue(result.contains("second"),
                "Cannot find second parent entry title");

        result = entryService.findChildren("title");
        assertNotNull(result, "Cannot get result");
        assertEquals(2, result.size(), "Result size don't match");
        assertTrue(result.contains("first"),
                "Cannot find first child entry title");
        assertTrue(result.contains("second"),
                "Cannot find second child entry title");
    }

    @DisplayName("Lock and unlock")
    @Test
    public void lockAndUnlock() {
        String title = "title";

        Entry entry = new Entry();
        entry.setTitle("Test");
        Optional<Entry> optionalEntry = Optional.of(entry);
        Mockito.when(entryDao.findByTitle("title")).thenReturn(optionalEntry);
        Optional<Entry> optionalEntry1 = Optional.empty();
        Mockito.when(entryDao.findByTitle("123")).thenReturn(optionalEntry1);
        Mockito.when(entryDao.create(Mockito.any(Entry.class))).thenReturn(true);

        Boolean result = entryService.lockEntry(title, true);
        assertTrue(result, "Lock an entry failed");
        result = entryService.lockEntry(title, false);
        assertTrue(result, "Unlock an entry failed");

        result = entryService.lockEntry("123", true);
        assertFalse(result, "Operate on a null entry");
    }
}