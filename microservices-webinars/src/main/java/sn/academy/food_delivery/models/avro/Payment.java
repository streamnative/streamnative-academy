/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package sn.academy.food_delivery.models.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Payment extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2064796509969062965L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Payment\",\"namespace\":\"sn.academy.food_delivery.models.avro\",\"fields\":[{\"name\":\"method_of_payment\",\"type\":{\"type\":\"record\",\"name\":\"PaymentMethod\",\"fields\":[{\"name\":\"type\",\"type\":[{\"type\":\"record\",\"name\":\"CreditCard\",\"fields\":[{\"name\":\"card_type\",\"type\":{\"type\":\"enum\",\"name\":\"CardType\",\"symbols\":[\"MASTERCARD\",\"AMEX\",\"VISA\",\"DISCOVER\"]}},{\"name\":\"account_number\",\"type\":\"string\"},{\"name\":\"billing_zip\",\"type\":\"string\"},{\"name\":\"ccv\",\"type\":\"string\"},{\"name\":\"expMonth\",\"type\":\"string\"},{\"name\":\"expYear\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"ApplePay\",\"fields\":[{\"name\":\"accountNumber\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"PayPal\",\"fields\":[{\"name\":\"accountNumber\",\"type\":\"string\"}]}]}]}},{\"name\":\"amount\",\"type\":{\"type\":\"record\",\"name\":\"PaymentAmount\",\"fields\":[{\"name\":\"food_total\",\"type\":\"float\"},{\"name\":\"tax\",\"type\":\"float\"},{\"name\":\"total\",\"type\":\"float\"}]}},{\"name\":\"isAuthorized\",\"type\":\"boolean\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Payment> ENCODER =
      new BinaryMessageEncoder<Payment>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Payment> DECODER =
      new BinaryMessageDecoder<Payment>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Payment> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Payment> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Payment> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Payment>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Payment to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Payment from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Payment instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Payment fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public sn.academy.food_delivery.models.avro.PaymentMethod method_of_payment;
  @Deprecated public sn.academy.food_delivery.models.avro.PaymentAmount amount;
  @Deprecated public boolean isAuthorized;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Payment() {}

  /**
   * All-args constructor.
   * @param method_of_payment The new value for method_of_payment
   * @param amount The new value for amount
   * @param isAuthorized The new value for isAuthorized
   */
  public Payment(sn.academy.food_delivery.models.avro.PaymentMethod method_of_payment, sn.academy.food_delivery.models.avro.PaymentAmount amount, java.lang.Boolean isAuthorized) {
    this.method_of_payment = method_of_payment;
    this.amount = amount;
    this.isAuthorized = isAuthorized;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return method_of_payment;
    case 1: return amount;
    case 2: return isAuthorized;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: method_of_payment = (sn.academy.food_delivery.models.avro.PaymentMethod)value$; break;
    case 1: amount = (sn.academy.food_delivery.models.avro.PaymentAmount)value$; break;
    case 2: isAuthorized = (java.lang.Boolean)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'method_of_payment' field.
   * @return The value of the 'method_of_payment' field.
   */
  public sn.academy.food_delivery.models.avro.PaymentMethod getMethodOfPayment() {
    return method_of_payment;
  }


  /**
   * Sets the value of the 'method_of_payment' field.
   * @param value the value to set.
   */
  public void setMethodOfPayment(sn.academy.food_delivery.models.avro.PaymentMethod value) {
    this.method_of_payment = value;
  }

  /**
   * Gets the value of the 'amount' field.
   * @return The value of the 'amount' field.
   */
  public sn.academy.food_delivery.models.avro.PaymentAmount getAmount() {
    return amount;
  }


  /**
   * Sets the value of the 'amount' field.
   * @param value the value to set.
   */
  public void setAmount(sn.academy.food_delivery.models.avro.PaymentAmount value) {
    this.amount = value;
  }

  /**
   * Gets the value of the 'isAuthorized' field.
   * @return The value of the 'isAuthorized' field.
   */
  public boolean getIsAuthorized() {
    return isAuthorized;
  }


  /**
   * Sets the value of the 'isAuthorized' field.
   * @param value the value to set.
   */
  public void setIsAuthorized(boolean value) {
    this.isAuthorized = value;
  }

  /**
   * Creates a new Payment RecordBuilder.
   * @return A new Payment RecordBuilder
   */
  public static sn.academy.food_delivery.models.avro.Payment.Builder newBuilder() {
    return new sn.academy.food_delivery.models.avro.Payment.Builder();
  }

  /**
   * Creates a new Payment RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Payment RecordBuilder
   */
  public static sn.academy.food_delivery.models.avro.Payment.Builder newBuilder(sn.academy.food_delivery.models.avro.Payment.Builder other) {
    if (other == null) {
      return new sn.academy.food_delivery.models.avro.Payment.Builder();
    } else {
      return new sn.academy.food_delivery.models.avro.Payment.Builder(other);
    }
  }

  /**
   * Creates a new Payment RecordBuilder by copying an existing Payment instance.
   * @param other The existing instance to copy.
   * @return A new Payment RecordBuilder
   */
  public static sn.academy.food_delivery.models.avro.Payment.Builder newBuilder(sn.academy.food_delivery.models.avro.Payment other) {
    if (other == null) {
      return new sn.academy.food_delivery.models.avro.Payment.Builder();
    } else {
      return new sn.academy.food_delivery.models.avro.Payment.Builder(other);
    }
  }

  /**
   * RecordBuilder for Payment instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Payment>
    implements org.apache.avro.data.RecordBuilder<Payment> {

    private sn.academy.food_delivery.models.avro.PaymentMethod method_of_payment;
    private sn.academy.food_delivery.models.avro.PaymentMethod.Builder method_of_paymentBuilder;
    private sn.academy.food_delivery.models.avro.PaymentAmount amount;
    private sn.academy.food_delivery.models.avro.PaymentAmount.Builder amountBuilder;
    private boolean isAuthorized;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(sn.academy.food_delivery.models.avro.Payment.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.method_of_payment)) {
        this.method_of_payment = data().deepCopy(fields()[0].schema(), other.method_of_payment);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (other.hasMethodOfPaymentBuilder()) {
        this.method_of_paymentBuilder = sn.academy.food_delivery.models.avro.PaymentMethod.newBuilder(other.getMethodOfPaymentBuilder());
      }
      if (isValidValue(fields()[1], other.amount)) {
        this.amount = data().deepCopy(fields()[1].schema(), other.amount);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (other.hasAmountBuilder()) {
        this.amountBuilder = sn.academy.food_delivery.models.avro.PaymentAmount.newBuilder(other.getAmountBuilder());
      }
      if (isValidValue(fields()[2], other.isAuthorized)) {
        this.isAuthorized = data().deepCopy(fields()[2].schema(), other.isAuthorized);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing Payment instance
     * @param other The existing instance to copy.
     */
    private Builder(sn.academy.food_delivery.models.avro.Payment other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.method_of_payment)) {
        this.method_of_payment = data().deepCopy(fields()[0].schema(), other.method_of_payment);
        fieldSetFlags()[0] = true;
      }
      this.method_of_paymentBuilder = null;
      if (isValidValue(fields()[1], other.amount)) {
        this.amount = data().deepCopy(fields()[1].schema(), other.amount);
        fieldSetFlags()[1] = true;
      }
      this.amountBuilder = null;
      if (isValidValue(fields()[2], other.isAuthorized)) {
        this.isAuthorized = data().deepCopy(fields()[2].schema(), other.isAuthorized);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'method_of_payment' field.
      * @return The value.
      */
    public sn.academy.food_delivery.models.avro.PaymentMethod getMethodOfPayment() {
      return method_of_payment;
    }


    /**
      * Sets the value of the 'method_of_payment' field.
      * @param value The value of 'method_of_payment'.
      * @return This builder.
      */
    public sn.academy.food_delivery.models.avro.Payment.Builder setMethodOfPayment(sn.academy.food_delivery.models.avro.PaymentMethod value) {
      validate(fields()[0], value);
      this.method_of_paymentBuilder = null;
      this.method_of_payment = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'method_of_payment' field has been set.
      * @return True if the 'method_of_payment' field has been set, false otherwise.
      */
    public boolean hasMethodOfPayment() {
      return fieldSetFlags()[0];
    }

    /**
     * Gets the Builder instance for the 'method_of_payment' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public sn.academy.food_delivery.models.avro.PaymentMethod.Builder getMethodOfPaymentBuilder() {
      if (method_of_paymentBuilder == null) {
        if (hasMethodOfPayment()) {
          setMethodOfPaymentBuilder(sn.academy.food_delivery.models.avro.PaymentMethod.newBuilder(method_of_payment));
        } else {
          setMethodOfPaymentBuilder(sn.academy.food_delivery.models.avro.PaymentMethod.newBuilder());
        }
      }
      return method_of_paymentBuilder;
    }

    /**
     * Sets the Builder instance for the 'method_of_payment' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public sn.academy.food_delivery.models.avro.Payment.Builder setMethodOfPaymentBuilder(sn.academy.food_delivery.models.avro.PaymentMethod.Builder value) {
      clearMethodOfPayment();
      method_of_paymentBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'method_of_payment' field has an active Builder instance
     * @return True if the 'method_of_payment' field has an active Builder instance
     */
    public boolean hasMethodOfPaymentBuilder() {
      return method_of_paymentBuilder != null;
    }

    /**
      * Clears the value of the 'method_of_payment' field.
      * @return This builder.
      */
    public sn.academy.food_delivery.models.avro.Payment.Builder clearMethodOfPayment() {
      method_of_payment = null;
      method_of_paymentBuilder = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'amount' field.
      * @return The value.
      */
    public sn.academy.food_delivery.models.avro.PaymentAmount getAmount() {
      return amount;
    }


    /**
      * Sets the value of the 'amount' field.
      * @param value The value of 'amount'.
      * @return This builder.
      */
    public sn.academy.food_delivery.models.avro.Payment.Builder setAmount(sn.academy.food_delivery.models.avro.PaymentAmount value) {
      validate(fields()[1], value);
      this.amountBuilder = null;
      this.amount = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'amount' field has been set.
      * @return True if the 'amount' field has been set, false otherwise.
      */
    public boolean hasAmount() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'amount' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public sn.academy.food_delivery.models.avro.PaymentAmount.Builder getAmountBuilder() {
      if (amountBuilder == null) {
        if (hasAmount()) {
          setAmountBuilder(sn.academy.food_delivery.models.avro.PaymentAmount.newBuilder(amount));
        } else {
          setAmountBuilder(sn.academy.food_delivery.models.avro.PaymentAmount.newBuilder());
        }
      }
      return amountBuilder;
    }

    /**
     * Sets the Builder instance for the 'amount' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public sn.academy.food_delivery.models.avro.Payment.Builder setAmountBuilder(sn.academy.food_delivery.models.avro.PaymentAmount.Builder value) {
      clearAmount();
      amountBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'amount' field has an active Builder instance
     * @return True if the 'amount' field has an active Builder instance
     */
    public boolean hasAmountBuilder() {
      return amountBuilder != null;
    }

    /**
      * Clears the value of the 'amount' field.
      * @return This builder.
      */
    public sn.academy.food_delivery.models.avro.Payment.Builder clearAmount() {
      amount = null;
      amountBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'isAuthorized' field.
      * @return The value.
      */
    public boolean getIsAuthorized() {
      return isAuthorized;
    }


    /**
      * Sets the value of the 'isAuthorized' field.
      * @param value The value of 'isAuthorized'.
      * @return This builder.
      */
    public sn.academy.food_delivery.models.avro.Payment.Builder setIsAuthorized(boolean value) {
      validate(fields()[2], value);
      this.isAuthorized = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'isAuthorized' field has been set.
      * @return True if the 'isAuthorized' field has been set, false otherwise.
      */
    public boolean hasIsAuthorized() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'isAuthorized' field.
      * @return This builder.
      */
    public sn.academy.food_delivery.models.avro.Payment.Builder clearIsAuthorized() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Payment build() {
      try {
        Payment record = new Payment();
        if (method_of_paymentBuilder != null) {
          try {
            record.method_of_payment = this.method_of_paymentBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("method_of_payment"));
            throw e;
          }
        } else {
          record.method_of_payment = fieldSetFlags()[0] ? this.method_of_payment : (sn.academy.food_delivery.models.avro.PaymentMethod) defaultValue(fields()[0]);
        }
        if (amountBuilder != null) {
          try {
            record.amount = this.amountBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("amount"));
            throw e;
          }
        } else {
          record.amount = fieldSetFlags()[1] ? this.amount : (sn.academy.food_delivery.models.avro.PaymentAmount) defaultValue(fields()[1]);
        }
        record.isAuthorized = fieldSetFlags()[2] ? this.isAuthorized : (java.lang.Boolean) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Payment>
    WRITER$ = (org.apache.avro.io.DatumWriter<Payment>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Payment>
    READER$ = (org.apache.avro.io.DatumReader<Payment>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










