#!/usr/bin/env python3
"""Turn a folder with src/ into a NetBeans "Java Application" project.

Copies the standard NetBeans project files (build.xml, manifest.mf,
nbproject/project.xml, project.properties, build-impl.xml) from nbtemplate/,
renaming the project and pointing main.class at main.Main, so the folder opens
with File > Open Project and builds with Clean and Build (javac source/target
1.8, the JDK of the lab machines).

Usage: python3 make_netbeans.py <project_dir> [...]     (or no args = all)
"""
import glob
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
TEMPLATE = os.path.join(HERE, "nbtemplate")
OLD_NAME = "08_J1SP0001_Bubble_sort_algorithm"


def make(project):
    name = os.path.basename(project.rstrip("/"))
    nb = os.path.join(project, "nbproject")
    os.makedirs(nb, exist_ok=True)
    os.makedirs(os.path.join(project, "test"), exist_ok=True)
    for fname, dest in (("build.xml", project), ("manifest.mf", project),
                        ("project.xml", nb), ("project.properties", nb),
                        ("build-impl.xml", nb)):
        text = open(os.path.join(TEMPLATE, fname), encoding="utf-8").read()
        text = text.replace(OLD_NAME, name)
        text = text.replace("the LAB211 starter project", "the LAB211 project")
        if fname == "project.properties":
            text = text.replace("main.class=ui.Main", "main.class=main.Main")
        with open(os.path.join(dest, fname), "w", encoding="utf-8") as fh:
            fh.write(text)
    keep = os.path.join(project, "test", ".gitkeep")
    open(keep, "w").close()
    with open(os.path.join(project, ".gitignore"), "w") as fh:
        fh.write("/build/\n/dist/\n/nbproject/private/\n*.class\n")


def main(argv):
    root = os.path.dirname(HERE)
    targets = argv or sorted(p for p in glob.glob(os.path.join(root, "HE176322_*"))
                             if os.path.isdir(p))
    for p in targets:
        make(p)
        print("NetBeans project:", os.path.basename(p.rstrip("/")))


if __name__ == "__main__":
    main(sys.argv[1:])
