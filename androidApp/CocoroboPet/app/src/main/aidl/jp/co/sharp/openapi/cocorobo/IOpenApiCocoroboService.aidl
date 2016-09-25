package jp.co.sharp.openapi.cocorobo;

interface IOpenApiCocoroboService {
  String control(String apiKey, String mode);
  String getData(String apiKey, String dataKind);
}
