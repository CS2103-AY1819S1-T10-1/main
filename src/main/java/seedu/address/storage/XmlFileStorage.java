package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores scheduler data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given scheduler data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableScheduler scheduler)
        throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, scheduler);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given toDoList data to the specified file.
     */
    public static void saveToDoListDataToFile(Path file, XmlSerializableToDoList toDoList)
        throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, toDoList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns scheduler in the file or an empty scheduler
     */
    public static XmlSerializableScheduler loadDataFromSaveFile(Path file) throws DataConversionException,
        FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableScheduler.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns toDoList in the file or an empty toDoList
     */
    public static XmlSerializableToDoList loadToDoListDataFromSaveFile(Path file) throws DataConversionException,
        FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableToDoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
