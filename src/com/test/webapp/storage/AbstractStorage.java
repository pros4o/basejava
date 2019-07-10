package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

import java.util.*;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void addToStorage(Resume resume, SK index);

    protected abstract void updateResumeInStorage(SK index, Resume resume);

    protected abstract void deleteResumeInStorage(SK index);

    protected abstract Resume getResumeFromStorage(SK index);

    protected abstract SK getKey(String uuid);

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK key = getSearchKeyIfExist(resume.getUuid());
        updateResumeInStorage(key, resume);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK key = getSearchKeyIfNotExist(resume.getUuid());
        addToStorage(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getSearchKeyIfExist(uuid);
        return getResumeFromStorage(key);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getSearchKeyIfExist(uuid);
        deleteResumeInStorage(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumeList = copyAll();
        Collections.sort(resumeList);
        return resumeList;
    }

    protected abstract List<Resume> copyAll();

    private SK getSearchKeyIfNotExist(String uuid) {
        SK key = getKey(uuid);
        if (checkKey(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getSearchKeyIfExist(String uuid) {
        SK key = getKey(uuid);
        if (!checkKey(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract boolean checkKey(SK index);
}
