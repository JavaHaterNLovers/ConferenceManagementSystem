package domain;


import java.io.File;
import java.util.Calendar;

/**
 *
 */
public class Proposal
{
    public Proposal() {}

    /**
     *
     */
    private int id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String keywords;

    /**
     *
     */
    private File file;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private String status;

    /**
     *
     */
    private Calendar modified;

    /**
     *
     */
    private Calendar created;
}