package fy.lgp.registrator

import com.esotericsoftware.kryo.Kryo
import org.apache.avro.generic.GenericRecord
import org.apache.spark.serializer.KryoRegistrator

class AvroKryoRegistrator extends KryoRegistrator {
  override def registerClasses(kryo: Kryo) {
    kryo.register(classOf[GenericRecord])
    ()
  }

}
