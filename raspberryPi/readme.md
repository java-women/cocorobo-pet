# ラズパイシステム
raspberry pi で使用するシステムのディレクトリ

## 機能一覧

- Webカメラ接続・撮影
- cognitiveAPI呼びだし
- cocoroboControlerの発話API呼びだし

## 無線設定

1.  Raspberry Piに無線ドングルを挿す。  
※今回はelecomの無線ドングルを使用  
以下のコマンドを実行し、無線ドングルが接続されていることを確認する。
```
$ lsusb
Bus 001 Device 035: ID 056e:4008 Elecom Co., Ltd
```
2. 以下のコマンドを実行する。
```
$ wpa_passphrase [SSID] [Password]
```
3. 下記のように無線設定を変更する。
```
$ sudo vi /etc/wpa_supplicant/wpa_supplicant.conf
trl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1
network={
        // ここに手順2．の実行結果をコピペする //        
        key_mgmt=WPA-PSK
        proto=RSN
        pairwise=CCMP TKIP
        group=CCMP TKIP
}
```
※環境に合わせて暗号方式などは変更してください。

4. 下記のようにネットワークインタフェースの設定を変更する。
```
$ sudo vi /etc/network/interfaces
auto lo
iface lo inet loopback
iface eth0 inet dhcp

auto wlan0
allow-hotplug wlan0
iface wlan0 inet dhcp
wireless-essid [SSID]

wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf
iface default inet dhcp
```
※上記はDHCPの例を記載。

5. 下記のコマンドを実行し、ネットワークインタフェースを再起動する。
```
$ sudo ifdown wlan0
$ sudo ifup wlan0
```

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
