package me.clip.deluxechat.fanciful;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
































public final class ArrayWrapper<E>
{
  private E[] _array;
  
  public ArrayWrapper(E... paramVarArgs)
  {
    setArray(paramVarArgs);
  }
  





  public E[] getArray()
  {
    return _array;
  }
  



  public void setArray(E[] paramArrayOfE)
  {
    Validate.notNull(paramArrayOfE, "The array must not be null.");
    _array = paramArrayOfE;
  }
  






  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ArrayWrapper))
    {
      return false;
    }
    return Arrays.equals(_array, _array);
  }
  






  public int hashCode()
  {
    return Arrays.hashCode(_array);
  }
  







  public static <T> T[] toArray(Iterable<? extends T> paramIterable, Class<T> paramClass)
  {
    int i = -1;
    if ((paramIterable instanceof Collection))
    {
      localObject1 = (Collection)paramIterable;
      i = ((Collection)localObject1).size();
    }
    

    if (i < 0) {
      i = 0;
      
      for (Iterator localIterator1 = paramIterable.iterator(); localIterator1.hasNext();) { localObject1 = (Object)localIterator1.next();
        i++;
      }
    }
    
    Object localObject1 = (Object[])Array.newInstance(paramClass, i);
    int j = 0;
    for (Object localObject2 : paramIterable) {
      localObject1[(j++)] = localObject2;
    }
    return localObject1;
  }
}
