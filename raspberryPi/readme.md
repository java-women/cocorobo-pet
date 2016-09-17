# ラズパイシステム
raspberry pi で使用するシステムのディレクトリ

## 機能一覧

- Webカメラ接続・撮影
- cognitiveAPI呼びだし
- cocoroboControlerの発話API呼びだし

## アプリケーション構成
```
cocorobo-pet
  ∟ gradle … Gradleラッパー格納ディレクトリ
  ∟ libs … Webcam Capture(http://webcam-capture.sarxos.pl/)のwebcam-capture-x.x.x.jar、bridj-*.jarを配置
  ∟ src … ソース格納ディレクトリ
  ∟ build.gradle … Gradle設定ファイル
  ∟ gradlew … Linux環境用Gradle実行ファイル
  ∟ gradlew.bat … Windows環境用Gradle実行ファイル
```