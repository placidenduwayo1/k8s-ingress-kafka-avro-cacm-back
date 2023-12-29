/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package fr.placide.cacmerbsmsmovement.domain.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Customer extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 2294372112758228206L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Customer\",\"namespace\":\"fr.placide.cacmerbsmsmovement.domain.avro\",\"fields\":[{\"name\":\"customerId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"firstname\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"lastname\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"createdAt\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"risk\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"status\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Customer> ENCODER =
      new BinaryMessageEncoder<Customer>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Customer> DECODER =
      new BinaryMessageDecoder<Customer>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Customer> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Customer> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Customer> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Customer>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Customer to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Customer from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Customer instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Customer fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String customerId;
  private java.lang.String firstname;
  private java.lang.String lastname;
  private java.lang.String createdAt;
  private java.lang.String risk;
  private java.lang.String status;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Customer() {}

  /**
   * All-args constructor.
   * @param customerId The new value for customerId
   * @param firstname The new value for firstname
   * @param lastname The new value for lastname
   * @param createdAt The new value for createdAt
   * @param risk The new value for risk
   * @param status The new value for status
   */
  public Customer(java.lang.String customerId, java.lang.String firstname, java.lang.String lastname, java.lang.String createdAt, java.lang.String risk, java.lang.String status) {
    this.customerId = customerId;
    this.firstname = firstname;
    this.lastname = lastname;
    this.createdAt = createdAt;
    this.risk = risk;
    this.status = status;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return customerId;
    case 1: return firstname;
    case 2: return lastname;
    case 3: return createdAt;
    case 4: return risk;
    case 5: return status;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: customerId = value$ != null ? value$.toString() : null; break;
    case 1: firstname = value$ != null ? value$.toString() : null; break;
    case 2: lastname = value$ != null ? value$.toString() : null; break;
    case 3: createdAt = value$ != null ? value$.toString() : null; break;
    case 4: risk = value$ != null ? value$.toString() : null; break;
    case 5: status = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'customerId' field.
   * @return The value of the 'customerId' field.
   */
  public java.lang.String getCustomerId() {
    return customerId;
  }


  /**
   * Sets the value of the 'customerId' field.
   * @param value the value to set.
   */
  public void setCustomerId(java.lang.String value) {
    this.customerId = value;
  }

  /**
   * Gets the value of the 'firstname' field.
   * @return The value of the 'firstname' field.
   */
  public java.lang.String getFirstname() {
    return firstname;
  }


  /**
   * Sets the value of the 'firstname' field.
   * @param value the value to set.
   */
  public void setFirstname(java.lang.String value) {
    this.firstname = value;
  }

  /**
   * Gets the value of the 'lastname' field.
   * @return The value of the 'lastname' field.
   */
  public java.lang.String getLastname() {
    return lastname;
  }


  /**
   * Sets the value of the 'lastname' field.
   * @param value the value to set.
   */
  public void setLastname(java.lang.String value) {
    this.lastname = value;
  }

  /**
   * Gets the value of the 'createdAt' field.
   * @return The value of the 'createdAt' field.
   */
  public java.lang.String getCreatedAt() {
    return createdAt;
  }


  /**
   * Sets the value of the 'createdAt' field.
   * @param value the value to set.
   */
  public void setCreatedAt(java.lang.String value) {
    this.createdAt = value;
  }

  /**
   * Gets the value of the 'risk' field.
   * @return The value of the 'risk' field.
   */
  public java.lang.String getRisk() {
    return risk;
  }


  /**
   * Sets the value of the 'risk' field.
   * @param value the value to set.
   */
  public void setRisk(java.lang.String value) {
    this.risk = value;
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public java.lang.String getStatus() {
    return status;
  }


  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(java.lang.String value) {
    this.status = value;
  }

  /**
   * Creates a new Customer RecordBuilder.
   * @return A new Customer RecordBuilder
   */
  public static fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder newBuilder() {
    return new fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder();
  }

  /**
   * Creates a new Customer RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Customer RecordBuilder
   */
  public static fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder newBuilder(fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder other) {
    if (other == null) {
      return new fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder();
    } else {
      return new fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder(other);
    }
  }

  /**
   * Creates a new Customer RecordBuilder by copying an existing Customer instance.
   * @param other The existing instance to copy.
   * @return A new Customer RecordBuilder
   */
  public static fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder newBuilder(fr.placide.cacmerbsmsmovement.domain.avro.Customer other) {
    if (other == null) {
      return new fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder();
    } else {
      return new fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder(other);
    }
  }

  /**
   * RecordBuilder for Customer instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Customer>
    implements org.apache.avro.data.RecordBuilder<Customer> {

    private java.lang.String customerId;
    private java.lang.String firstname;
    private java.lang.String lastname;
    private java.lang.String createdAt;
    private java.lang.String risk;
    private java.lang.String status;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.customerId)) {
        this.customerId = data().deepCopy(fields()[0].schema(), other.customerId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.firstname)) {
        this.firstname = data().deepCopy(fields()[1].schema(), other.firstname);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.lastname)) {
        this.lastname = data().deepCopy(fields()[2].schema(), other.lastname);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[3].schema(), other.createdAt);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.risk)) {
        this.risk = data().deepCopy(fields()[4].schema(), other.risk);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.status)) {
        this.status = data().deepCopy(fields()[5].schema(), other.status);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
    }

    /**
     * Creates a Builder by copying an existing Customer instance
     * @param other The existing instance to copy.
     */
    private Builder(fr.placide.cacmerbsmsmovement.domain.avro.Customer other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.customerId)) {
        this.customerId = data().deepCopy(fields()[0].schema(), other.customerId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.firstname)) {
        this.firstname = data().deepCopy(fields()[1].schema(), other.firstname);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.lastname)) {
        this.lastname = data().deepCopy(fields()[2].schema(), other.lastname);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[3].schema(), other.createdAt);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.risk)) {
        this.risk = data().deepCopy(fields()[4].schema(), other.risk);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.status)) {
        this.status = data().deepCopy(fields()[5].schema(), other.status);
        fieldSetFlags()[5] = true;
      }
    }

    /**
      * Gets the value of the 'customerId' field.
      * @return The value.
      */
    public java.lang.String getCustomerId() {
      return customerId;
    }


    /**
      * Sets the value of the 'customerId' field.
      * @param value The value of 'customerId'.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder setCustomerId(java.lang.String value) {
      validate(fields()[0], value);
      this.customerId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'customerId' field has been set.
      * @return True if the 'customerId' field has been set, false otherwise.
      */
    public boolean hasCustomerId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'customerId' field.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder clearCustomerId() {
      customerId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'firstname' field.
      * @return The value.
      */
    public java.lang.String getFirstname() {
      return firstname;
    }


    /**
      * Sets the value of the 'firstname' field.
      * @param value The value of 'firstname'.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder setFirstname(java.lang.String value) {
      validate(fields()[1], value);
      this.firstname = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'firstname' field has been set.
      * @return True if the 'firstname' field has been set, false otherwise.
      */
    public boolean hasFirstname() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'firstname' field.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder clearFirstname() {
      firstname = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'lastname' field.
      * @return The value.
      */
    public java.lang.String getLastname() {
      return lastname;
    }


    /**
      * Sets the value of the 'lastname' field.
      * @param value The value of 'lastname'.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder setLastname(java.lang.String value) {
      validate(fields()[2], value);
      this.lastname = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'lastname' field has been set.
      * @return True if the 'lastname' field has been set, false otherwise.
      */
    public boolean hasLastname() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'lastname' field.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder clearLastname() {
      lastname = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'createdAt' field.
      * @return The value.
      */
    public java.lang.String getCreatedAt() {
      return createdAt;
    }


    /**
      * Sets the value of the 'createdAt' field.
      * @param value The value of 'createdAt'.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder setCreatedAt(java.lang.String value) {
      validate(fields()[3], value);
      this.createdAt = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'createdAt' field has been set.
      * @return True if the 'createdAt' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'createdAt' field.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder clearCreatedAt() {
      createdAt = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'risk' field.
      * @return The value.
      */
    public java.lang.String getRisk() {
      return risk;
    }


    /**
      * Sets the value of the 'risk' field.
      * @param value The value of 'risk'.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder setRisk(java.lang.String value) {
      validate(fields()[4], value);
      this.risk = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'risk' field has been set.
      * @return True if the 'risk' field has been set, false otherwise.
      */
    public boolean hasRisk() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'risk' field.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder clearRisk() {
      risk = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public java.lang.String getStatus() {
      return status;
    }


    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder setStatus(java.lang.String value) {
      validate(fields()[5], value);
      this.status = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public fr.placide.cacmerbsmsmovement.domain.avro.Customer.Builder clearStatus() {
      status = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Customer build() {
      try {
        Customer record = new Customer();
        record.customerId = fieldSetFlags()[0] ? this.customerId : (java.lang.String) defaultValue(fields()[0]);
        record.firstname = fieldSetFlags()[1] ? this.firstname : (java.lang.String) defaultValue(fields()[1]);
        record.lastname = fieldSetFlags()[2] ? this.lastname : (java.lang.String) defaultValue(fields()[2]);
        record.createdAt = fieldSetFlags()[3] ? this.createdAt : (java.lang.String) defaultValue(fields()[3]);
        record.risk = fieldSetFlags()[4] ? this.risk : (java.lang.String) defaultValue(fields()[4]);
        record.status = fieldSetFlags()[5] ? this.status : (java.lang.String) defaultValue(fields()[5]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Customer>
    WRITER$ = (org.apache.avro.io.DatumWriter<Customer>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Customer>
    READER$ = (org.apache.avro.io.DatumReader<Customer>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.customerId);

    out.writeString(this.firstname);

    out.writeString(this.lastname);

    out.writeString(this.createdAt);

    out.writeString(this.risk);

    out.writeString(this.status);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.customerId = in.readString();

      this.firstname = in.readString();

      this.lastname = in.readString();

      this.createdAt = in.readString();

      this.risk = in.readString();

      this.status = in.readString();

    } else {
      for (int i = 0; i < 6; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.customerId = in.readString();
          break;

        case 1:
          this.firstname = in.readString();
          break;

        case 2:
          this.lastname = in.readString();
          break;

        case 3:
          this.createdAt = in.readString();
          break;

        case 4:
          this.risk = in.readString();
          break;

        case 5:
          this.status = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










