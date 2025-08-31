{
  description = "Flake for development";
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
  };
  outputs = {
    self,
    nixpkgs,
  }: let
    pkgs = nixpkgs.legacyPackages."x86_64-linux";
    lib = nixpkgs.lib;
  in {
    devShells."x86_64-linux".default = pkgs.mkShell {
      packages = [pkgs.jdk];
      LD_LIBRARY_PATH = lib.makeLibraryPath [pkgs.libglvnd];
    };
  };
}
