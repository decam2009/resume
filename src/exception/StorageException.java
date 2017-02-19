package exception;

/**
 * Created by BORIS on 19.01.17.
 */
public class StorageException extends RuntimeException
  {
    String uuid;

	public StorageException(String message, String uuid, Exception e)
	  {
	    super(message);
	    this.uuid = uuid;

	  }

	public StorageException(Exception e)
	  {
 		this (e.getMessage(), null, e);
	  }

	public String getUuid()
	  {
	    return uuid;
	  }

	public StorageException (String message, String uuid)
	  {
	     super(message);
	     this.uuid = uuid;
	  }
  }
