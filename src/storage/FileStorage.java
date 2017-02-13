package storage;

import exception.StorageException;
import model.Resume;
import serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by BORIS on 29.01.17.
 */
public class FileStorage extends AbstractStorage<File>
  {
	private File directory;
	protected StreamSerializer streamSerializer;

    protected FileStorage(File directory, StreamSerializer streamSerializer)
	  {
		Objects.requireNonNull(directory, "Directory must not be empty");
		this.streamSerializer = streamSerializer;
		if (!directory.isDirectory())
		  throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
		if (!directory.canRead() || !directory.canWrite())
		  throw new IllegalArgumentException(directory.getAbsolutePath() + " no read/write privelege");
		this.directory = directory;
	  }

	@Override
	public int size()
	  {
	    return directory.listFiles().length;
	  }

	@Override
	public void clear()
	  {
		for (File f: directory.listFiles())
		  doDelete(f);
	  }

	@Override
	public Resume[] getAll()
	  {
		Resume[] tmp = new Resume[directory.listFiles().length];
		File[] files = directory.listFiles();
		for (int i = 0; i <= files.length - 1; i++)
		  {
		    tmp[i] = doGet(files[i]);
		  }
	    return tmp;
	  }

	@Override
	protected File getSearchKey(String uuid)
	  {
	    return new File(directory, uuid);
	  }

	@Override
	protected void doSave(Resume r, File file)
	  {
		try
		  {
		    file.createNewFile();
		    doUpdate(r, file);
		  }
		catch (IOException e)
		  {
		    throw new StorageException("IO error", file.getName(), e);
		  }
	  }

	@Override
	protected void doUpdate(Resume oldR, File file)
	  {
		try
		  {
		    streamSerializer.doWrite(oldR, new BufferedOutputStream(new FileOutputStream(file)));
		  }
		catch (IOException e)
		  {
		    throw new StorageException("IO error", file.getName(), e);
		  }
	  }

	@Override
	protected Resume doGet(File file)
	  {
		try
		  {
		    return streamSerializer.doRead (new BufferedInputStream(new FileInputStream(file)));
		  }
		catch (IOException e)
		  {
		    throw new StorageException("IO error", file.getName(), e);
		  }
	  }

	@Override
	protected List<Resume> doGetAllSorted()
	{
	  List<Resume> tmp = new ArrayList<>();

	  for (File f: directory.listFiles())
	    {
		  try
		    {
			  tmp.add(streamSerializer.doRead(new BufferedInputStream(new FileInputStream(f))));
		    }
		  catch (IOException e)
		    {
		   	  throw new StorageException("IO error", f.getName(), e);
		    }
		}
	  return tmp;
	}

	@Override
	protected boolean isExist(File file)
	  {
	    return file.exists();
	  }

	@Override
	protected void doDelete(File file)
	  {
   		for (File f: directory.listFiles())
		  {
		  	if (f.getName().equals(file.getName()))
			  {
			     f.delete();
			  }
		  }
	  }
  }