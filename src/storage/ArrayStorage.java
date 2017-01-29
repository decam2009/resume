package storage;

import model.Resume;

/**
 * Created by BORIS on 23.01.17.
 */
public class ArrayStorage extends AbstractArrayStorage
  {
	@Override
	protected void fillDeletedElement(int index)
	  {
		storage[index] = storage[size - 1];
	  }

	@Override
	protected void insertElement(Resume r, int index)
	  {
		storage[size] = r;
	  }

	@Override
	protected Integer getSearchKey(String uuid)
	  {
		for (int i = 0; i < size; i++) {
		  if (uuid.equals(storage[i].getUuid())) {
			return i;
		  }
		}
		return -1;
	  }

  }