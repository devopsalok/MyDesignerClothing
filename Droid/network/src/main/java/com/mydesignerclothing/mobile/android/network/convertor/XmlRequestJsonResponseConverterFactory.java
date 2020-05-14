package com.mydesignerclothing.mobile.android.network.convertor;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class XmlRequestJsonResponseConverterFactory extends Converter.Factory {
  private Strategy strategy = new AnnotationStrategy();
  private final Serializer serializer = new Persister(strategy);

  @Nullable
  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
    return GsonConverterFactory.create().responseBodyConverter(type, annotations, retrofit);
  }

  @Nullable
  @Override
  public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
    return SimpleXmlConverterFactory.create(serializer).requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
  }

  @Nullable
  @Override
  @SuppressWarnings("EmptyMethod")
  public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
    return super.stringConverter(type, annotations, retrofit);
  }
}
