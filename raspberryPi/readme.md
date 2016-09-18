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
  ∟ src … ソース格納ディレクトリ
  ∟ build.gradle … Gradle設定ファイル
  ∟ gradlew … Linux環境用Gradle実行ファイル
  ∟ gradlew.bat … Windows環境用Gradle実行ファイル
```

## 構築手順
1. Raspberry Piで下記のコマンドを実行し、fswebcamをインストールする。
```
$ sudo apt-get install fswebcam
```

2. Raspberry PiにWebカメラを接続し、下記のコマンドを実行し、Webカメラが接続されていることを確認する。
```
pi@raspberrypi:~/cocorobo$ lsusb
Bus 001 Device 037: ID 046d:0825 Logitech, Inc. Webcam C270
```

3. 下記のコマンドを実行し、生成したアプリを実行する。
```
$ java -jar cocorobo-pet.war &
```
