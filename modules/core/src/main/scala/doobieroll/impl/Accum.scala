package doobieroll.impl;
import scala.collection.mutable
import Accum._

private[doobieroll] class Accum() {

  val seenRootDbItem: mutable.Set[Any] = mutable.Set.empty
  val rootDbItems: mutable.ArrayBuffer[Any] = mutable.ArrayBuffer.empty[Any]
  // For storing raw DB values (with the parentId as the key) until
  // all child values for reach parent is available
  val rawLookup: LookupByIdx[Any] = mutable.Map.empty[Int, AnyKeyMultiMap[Any]]

  def addRootDbItem(k: Any, v: Any): Unit =
    if (seenRootDbItem.add(k))
      rootDbItems += v

  def getRootDbItems[A]: Iterator[A] =
    rootDbItems.iterator.asInstanceOf[Iterator[A]]

  def getRawLookup[A](
    idx: Int,
  ): AnyKeyMultiMap[A] =
    rawLookup
      .getOrElseUpdate(idx, mutable.Map.empty[Any, mutable.ArrayBuffer[Any]])
      .asInstanceOf[AnyKeyMultiMap[A]]

}

private[doobieroll] object Accum {

  type LookupByIdx[A] = mutable.Map[Int, AnyKeyMultiMap[A]]
  type AnyKeyMultiMap[A] = mutable.Map[Any, mutable.ArrayBuffer[A]]

}
